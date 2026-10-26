package edu.ict.shop;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.ict.shop.model.*;

public class Main {
    private final Shop shop;
    private final Scanner input;

    public Main(Shop shop, Scanner input) {
        this.shop = shop;
        this.input = input;
    }

    public static void main(String[] args) {
        new Main(new Shop(), new Scanner(System.in)).run();
    }

    public void run() {
        System.out.println("=== ICT Console Shop ===");
        System.out.println("Employee: employee@shop.local / Employee123");
        System.out.println("Customer: customer@shop.local / Customer123");
        try {
            while (true) {
                System.out.println("\nMAIN MENU\n1. Log in\n2. Register customer\n3. List products\n0. Exit");
                int choice = readInt("Choose: ");
                if (choice == 0) { break; }
                try {
                    switch (choice) {
                        case 1: login(); break;
                        case 2: addUser(false); break;
                        case 3: showProducts(); break;
                        default: System.out.println("Unknown option.");
                    }
                } catch (IllegalArgumentException error) {
                    System.out.println("Error: " + error.getMessage());
                }
            }
            System.out.println("Goodbye.");
        } catch (NoSuchElementException error) {
            System.out.println("\nInput closed. Goodbye.");
        }
    }

    private void login() {
        String email = readText("Email: ");
        String password = readText("Password: ");
        User user = shop.login(email, password);
        if (user == null) {
            System.out.println("Invalid email or password.");
        } else if (user instanceof Employee) {
            employeeMenu();
        } else {
            customerMenu((Customer) user);
        }
    }

    private void employeeMenu() {
        while (true) {
            System.out.println("\nEMPLOYEE MENU\n1. List products\n2. Add product"
                    + "\n3. List users\n4. Add customer\n5. Add employee\n6. View all orders\n0. Log out");
            int choice = readInt("Choose: ");
            if (choice == 0) { return; }
            try {
                switch (choice) {
                    case 1: showProducts(); break;
                    case 2: addProduct(); break;
                    case 3:
                        for (User user : shop.getUsers()) { System.out.println(user); }
                        break;
                    case 4: addUser(false); break;
                    case 5: addUser(true); break;
                    case 6: showOrders(shop.getOrders()); break;
                    default: System.out.println("Unknown option.");
                }
            } catch (IllegalArgumentException error) {
                System.out.println("Error: " + error.getMessage());
            }
        }
    }

    private void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\nCUSTOMER MENU\n1. List products\n2. Place order\n3. My orders\n0. Log out");
            int choice = readInt("Choose: ");
            if (choice == 0) { return; }
            try {
                switch (choice) {
                    case 1: showProducts(); break;
                    case 2: placeOrder(customer); break;
                    case 3: showOrders(shop.getCustomerOrders(customer)); break;
                    default: System.out.println("Unknown option.");
                }
            } catch (IllegalArgumentException error) {
                System.out.println("Error: " + error.getMessage());
            }
        }
    }

    private void addUser(boolean employee) {
        String name = readText("Name: ");
        String email = readText("Email: ");
        String password = readText("Password (at least 4 characters): ");
        User user;
        if (employee) {
            user = shop.addEmployee(name, email, password);
        } else {
            user = shop.addCustomer(name, email, password);
        }
        System.out.println("User added: " + user);
    }

    private void addProduct() {
        int type = readInt("Type: 1 Book, 2 Electronics, 3 Clothing: ");
        if (type < 1 || type > 3) {
            throw new IllegalArgumentException("Choose a type from 1 to 3.");
        }
        int id = shop.getProducts().size() + 1;
        String name = readText("Name: ");
        double price = readPrice();
        int stock = readInt("Stock: ");
        Product product;
        if (type == 1) {
            product = new Book(id, name, price, stock, readText("Author: "));
        } else if (type == 2) {
            product = new Electronics(id, name, price, stock, readText("Brand: "));
        } else {
            product = new Clothing(id, name, price, stock, readText("Size: "));
        }
        shop.addProduct(product);
        System.out.println("Product added: " + product.displayInfo());
    }

    private void placeOrder(Customer customer) {
        ArrayList<Product> selected = new ArrayList<>();
        showProducts();
        System.out.println("One selection = one unit. Repeat an ID for more units. Enter 0 to finish.");
        while (true) {
            int id = readInt("Product ID: ");
            if (id == 0) { break; }
            Product product = shop.findProduct(id);
            selected.add(product);
            System.out.println("Added: " + product.getName());
        }
        if (selected.isEmpty()) {
            System.out.println("Order cancelled: no products selected.");
        } else if (readText("Confirm order? (y/n): ").equalsIgnoreCase("y")) {
            Order order = shop.placeOrder(customer, selected);
            System.out.println("Order placed.");
            System.out.print(order.printReceipt());
        } else {
            System.out.println("Order cancelled.");
        }
    }

    private void showProducts() {
        if (shop.getProducts().isEmpty()) { System.out.println("No products."); }
        for (Product product : shop.getProducts()) {
            System.out.println(product.displayInfo());
        }
    }

    private void showOrders(ArrayList<Order> orders) {
        if (orders.isEmpty()) { System.out.println("No orders yet."); }
        for (Order order : orders) { System.out.print(order.printReceipt()); }
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) { return value; }
            System.out.println("Enter a value.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException error) {
                System.out.println("Enter an integer.");
            }
        }
    }

    private double readPrice() {
        while (true) {
            try {
                double value = Double.parseDouble(readText("Price in KZT: "));
                if (Double.isFinite(value) && value >= 0) { return value; }
            } catch (NumberFormatException ignored) {
                // Ask again after text that is not a number.
            }
            System.out.println("Enter a finite, non-negative price.");
        }
    }
}
