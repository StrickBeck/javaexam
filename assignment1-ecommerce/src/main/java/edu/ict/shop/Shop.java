package edu.ict.shop;

import java.util.ArrayList;
import edu.ict.shop.model.*;

public final class Shop {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<Order> orders = new ArrayList<>();

    public Shop() {
        addEmployee("Demo Employee", "employee@shop.local", "Employee123");
        addCustomer("Demo Customer", "customer@shop.local", "Customer123");
        addProduct(new Book(1, "Java Basics", 3500, 10, "ICT Teaching Team"));
        addProduct(new Electronics(2, "Wireless Mouse", 15000, 5, "StudyTech"));
        addProduct(new Clothing(3, "Campus T-Shirt", 8000, 8, "M"));
    }

    public Customer addCustomer(String name, String email, String password) {
        checkEmail(email);
        Customer customer = new Customer(users.size() + 1, name, email, password);
        users.add(customer);
        return customer;
    }

    public Employee addEmployee(String name, String email, String password) {
        checkEmail(email);
        Employee employee = new Employee(users.size() + 1, name, email, password);
        users.add(employee);
        return employee;
    }

    private void checkEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email is required.");
        }
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email.trim())) {
                throw new IllegalArgumentException("This email is already registered.");
            }
        }
    }

    public User login(String email, String password) {
        if (email == null) { return null; }
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email.trim()) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is required.");
        }
        for (Product existing : products) {
            if (existing.getId() == product.getId()) {
                throw new IllegalArgumentException("Product ID already exists.");
            }
        }
        products.add(product);
    }

    public ArrayList<User> getUsers() { return new ArrayList<>(users); }
    public ArrayList<Product> getProducts() { return new ArrayList<>(products); }
    public ArrayList<Order> getOrders() { return new ArrayList<>(orders); }

    public Product findProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) { return product; }
        }
        throw new IllegalArgumentException("Product was not found.");
    }

    public ArrayList<Order> getCustomerOrders(Customer customer) {
        ArrayList<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomer() == customer) { result.add(order); }
        }
        return result;
    }

    public Order placeOrder(Customer customer, ArrayList<Product> selected) {
        if (customer == null || !users.contains(customer)) {
            throw new IllegalArgumentException("Choose a registered customer.");
        }
        if (selected == null || selected.isEmpty()) {
            throw new IllegalArgumentException("The order is empty.");
        }
        for (Product product : selected) {
            if (!products.contains(product)) {
                throw new IllegalArgumentException("The order contains an unknown product.");
            }
        }

        // Each occurrence in the list means one unit of that product.
        for (Product product : products) {
            int wanted = 0;
            for (Product chosen : selected) {
                if (chosen == product) { wanted++; }
            }
            if (wanted > product.getStockQuantity()) {
                throw new IllegalArgumentException("Not enough stock for " + product.getName());
            }
        }

        // Nothing is deducted until every product has passed the checks.
        Order order = new Order(orders.size() + 1, customer, selected);
        for (Product product : selected) {
            product.setStockQuantity(product.getStockQuantity() - 1);
        }
        orders.add(order);
        return order;
    }
}
