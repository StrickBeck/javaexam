package edu.ict.shop;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import edu.ict.shop.model.*;

/** Optional checks, run separately from the application. */
public class ShopTests {
    private static int checks;

    public static void main(String[] args) {
        modelChecks();
        accountChecks();
        purchaseChecks();
        rejectionChecks();
        receiptChecks();
        consoleChecks();
        inputChecks();
        encapsulationChecks();
        System.out.println("PASS: " + checks + " checks across 8 groups.");
    }

    private static Customer customer(Shop shop) {
        return (Customer) shop.login("customer@shop.local", "Customer123");
    }

    private static ArrayList<Product> selection(Product... products) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) { result.add(product); }
        return result;
    }

    private static void modelChecks() {
        Shop shop = new Shop();
        check(shop.getProducts().size() == 3, "Three demo products");
        check(shop.getUsers().size() == 2, "Two demo users");
        check(shop.findProduct(1) instanceof Book, "Book inheritance");
        check(shop.findProduct(2) instanceof Electronics, "Electronics inheritance");
        check(shop.findProduct(3) instanceof Clothing, "Clothing inheritance");
        check(shop.findProduct(1).displayInfo().contains("author="), "Book overriding");
        check(shop.findProduct(2).displayInfo().contains("brand="), "Electronics overriding");
        check(shop.findProduct(3).displayInfo().contains("size="), "Clothing overriding");
        rejects(() -> new Product(0, "Invalid", 1, 1), "Invalid ID");
        rejects(() -> new Product(4, "", 1, 1), "Empty name");
        rejects(() -> new Product(4, "Invalid", -1, 1), "Negative price");
        rejects(() -> new Product(4, "Invalid", Double.NaN, 1), "NaN price");
        rejects(() -> new Product(4, "Invalid", Double.POSITIVE_INFINITY, 1), "Infinite price");
        rejects(() -> new Product(4, "Invalid", 1, -1), "Negative stock");
        rejects(() -> new Book(4, "Book", 1, 1, ""), "Empty author");
        rejects(() -> shop.findProduct(999), "Missing product");
        rejects(() -> shop.addProduct(new Book(1, "Duplicate", 1, 1, "Author")), "Duplicate ID");
    }

    private static void accountChecks() {
        Shop shop = new Shop();
        check(customer(shop).getRole().equals("Customer"), "Customer role");
        check(shop.login("employee@shop.local", "Employee123") instanceof Employee, "Employee login");
        check(shop.login(" CUSTOMER@SHOP.LOCAL ", "Customer123") == customer(shop), "Email case");
        check(shop.login("customer@shop.local", "wrong") == null, "Wrong password");
        check(shop.login("missing@shop.local", "anything") == null, "Missing account");
        rejects(() -> shop.addCustomer("Duplicate", " CUSTOMER@SHOP.LOCAL ", "1234"), "Duplicate email");
        rejects(() -> shop.addCustomer("Invalid", "missing-at-sign", "1234"), "Email check");
        rejects(() -> shop.addCustomer("Invalid", "new@shop.local", "12"), "Password check");
        rejects(() -> shop.addEmployee("", "new@shop.local", "1234"), "Name check");
        check(shop.getUsers().size() == 2, "Invalid users not saved");
        Customer added = shop.addCustomer("New", "new@shop.local", "1234");
        check(added.getId() == 3, "Sequential IDs");
        Employee staff = shop.addEmployee("Staff", "staff@shop.local", "1234");
        check(shop.login("staff@shop.local", "1234") == staff, "New employee login");
        check(!added.toString().contains("1234"), "Password not listed");
    }

    private static void purchaseChecks() {
        Shop shop = new Shop();
        Product book = shop.findProduct(1);
        Product mouse = shop.findProduct(2);
        Order order = shop.placeOrder(customer(shop), selection(book, book, mouse));
        check(order.getOrderId() == 1, "Order ID");
        check(order.getProducts().size() == 3, "One list entry per unit");
        check(order.getTotalAmount() == 22000.0, "Total 22000");
        check(book.getStockQuantity() == 8, "Two books deducted");
        check(mouse.getStockQuantity() == 4, "One mouse deducted");
        check(order.printReceipt().contains("TOTAL: 22000.00 KZT"), "Receipt total");
        check(shop.getOrders().size() == 1, "Order saved");
        Customer other = shop.addCustomer("Other", "other@shop.local", "1234");
        shop.placeOrder(other, selection(mouse));
        check(shop.getCustomerOrders(customer(shop)).size() == 1, "Own history");
        check(shop.getCustomerOrders(other).size() == 1, "Other customer's history");
        check(shop.getOrders().size() == 2, "All orders");
        book.setPrice(0);
        check(shop.placeOrder(customer(shop), selection(book)).getTotalAmount() == 0, "Free product");
    }

    private static void rejectionChecks() {
        Shop shop = new Shop();
        Product book = shop.findProduct(1);
        Product mouse = shop.findProduct(2);
        mouse.setStockQuantity(1);
        rejects(() -> shop.placeOrder(customer(shop), selection(book, mouse, mouse)), "Combined demand");
        check(book.getStockQuantity() == 10, "No partial deduction");
        check(mouse.getStockQuantity() == 1, "Rejected product unchanged");
        check(shop.getOrders().isEmpty(), "Rejected order not saved");
        rejects(() -> shop.placeOrder(customer(shop), new ArrayList<>()), "Empty selection");
        rejects(() -> shop.placeOrder(customer(shop), null), "Null selection");
        rejects(() -> shop.placeOrder(null, selection(book)), "No customer");
        Customer outsider = new Customer(2, "Outside", "outside@shop.local", "1234");
        rejects(() -> shop.placeOrder(outsider, selection(book)), "Foreign customer");
        Product foreign = new Product(1, "Outside", 1, 1);
        rejects(() -> shop.placeOrder(customer(shop), selection(foreign)), "Foreign product");
        rejects(() -> shop.placeOrder(customer(shop), selection((Product) null)), "Null product");
        book.setStockQuantity(0);
        rejects(() -> shop.placeOrder(customer(shop), selection(book)), "No stock");
        check(shop.placeOrder(customer(shop), selection(mouse)).getOrderId() == 1, "Failure keeps next ID");
        rejects(() -> mouse.setStockQuantity(-1), "Invalid setter");
        Product expensive = new Product(4, "Huge price", Double.MAX_VALUE, 2);
        shop.addProduct(expensive);
        rejects(() -> shop.placeOrder(customer(shop), selection(expensive, expensive)), "Total overflow");
        check(expensive.getStockQuantity() == 2, "Overflow keeps stock");
    }

    private static void receiptChecks() {
        Shop shop = new Shop();
        Product book = shop.findProduct(1);
        ArrayList<Product> selected = selection(book);
        Order order = shop.placeOrder(customer(shop), selected);
        String original = order.printReceipt();
        selected.clear();
        book.setName("New name");
        book.setPrice(9000);
        check(order.printReceipt().equals(original), "Receipt remains unchanged");
        check(order.getTotalAmount() == 3500, "Stored total unchanged");
        order.getProducts().clear();
        check(order.getProducts().size() == 1, "Order list copy");
        shop.getProducts().clear();
        check(shop.getProducts().size() == 3, "Catalogue list copy");
        shop.getUsers().clear();
        check(shop.getUsers().size() == 2, "User list copy");
        shop.getOrders().clear();
        check(shop.getOrders().size() == 1, "Order list copy");
    }

    private static void consoleChecks() {
        Shop shop = new Shop();
        String script = String.join("\n",
                "1", "employee@shop.local", "Employee123",
                "2", "1", "New Book", "bad", "-1", "NaN", "1000", "3", "New Author",
                "2", "2", "New Device", "2000", "4", "Brand",
                "2", "3", "New Shirt", "3000", "5", "L",
                "4", "New Customer", "new@shop.local", "1234",
                "5", "New Employee", "staff@shop.local", "1234",
                "3", "6", "0",
                "1", "customer@shop.local", "Customer123",
                "2", "1", "1", "2", "0", "y", "3", "0",
                "1", "employee@shop.local", "Employee123", "6", "0", "0") + "\n";
        String output = runConsole(shop, script);
        check(output.contains("Product added: Book"), "Menu adds book");
        check(output.contains("Product added: Electronics"), "Menu adds electronics");
        check(output.contains("Product added: Clothing"), "Menu adds clothing");
        check(output.contains("User added:"), "Menu adds user");
        check(output.contains("Enter a finite, non-negative price."), "Price input recovery");
        check(output.contains("TOTAL: 22000.00 KZT"), "Menu checkout");
        check(output.contains("Goodbye."), "Menu exit");
        check(shop.getProducts().size() == 6, "All product types added");
        check(shop.getUsers().size() == 4, "Both user types added");
        check(shop.getOrders().size() == 1, "Menu saves one order");
    }

    private static void inputChecks() {
        Shop shop = new Shop();
        shop.findProduct(1).setStockQuantity(1);
        String script = String.join("\n",
                "abc", "9", "1", "customer@shop.local", "wrong",
                "1", "customer@shop.local", "Customer123",
                "2", "0", "2", "1", "0", "n",
                "2", "1", "1", "0", "y", "2", "999", "0", "0") + "\n";
        String output = runConsole(shop, script);
        check(output.contains("Enter an integer."), "Numeric input recovery");
        check(output.contains("Unknown option."), "Unknown menu option");
        check(output.contains("Invalid email or password."), "Login failure shown");
        check(output.contains("no products selected"), "Empty order cancelled");
        check(output.contains("Order cancelled."), "Customer can cancel");
        check(output.contains("Not enough stock"), "Stock error shown");
        check(output.contains("Product was not found."), "Missing product shown");
        check(shop.getOrders().isEmpty(), "No failed/cancelled orders saved");
        check(shop.findProduct(1).getStockQuantity() == 1, "Stock remains unchanged");
        check(runConsole(shop, "1\n").contains("Input closed."), "EOF exit");
    }

    private static void encapsulationChecks() {
        Class<?>[] classes = {User.class, Customer.class, Employee.class, Product.class,
                Book.class, Electronics.class, Clothing.class, Order.class, Shop.class, Main.class};
        for (Class<?> type : classes) {
            for (Field field : type.getDeclaredFields()) {
                check(Modifier.isPrivate(field.getModifiers()),
                        type.getSimpleName() + "." + field.getName() + " is private");
            }
        }
    }

    private static String runConsole(Shop shop, String script) {
        PrintStream original = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            new Main(shop, new Scanner(script)).run();
        } finally {
            System.setOut(original);
        }
        return bytes.toString(StandardCharsets.UTF_8);
    }

    private static void rejects(Runnable action, String label) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            checks++;
            return;
        }
        throw new AssertionError(label + ": expected rejection");
    }

    private static void check(boolean condition, String label) {
        if (!condition) { throw new AssertionError(label); }
        checks++;
    }
}
