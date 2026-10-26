package edu.ict.shop.model;

import java.util.ArrayList;
import java.util.Locale;

public class Order {
    private final int orderId;
    private final Customer customer;
    private final ArrayList<Product> products;
    private final double totalAmount;
    private final String receipt;

    public Order(int orderId, Customer customer, ArrayList<Product> products) {
        if (orderId <= 0 || customer == null || products == null || products.isEmpty()) {
            throw new IllegalArgumentException("An order needs an ID, customer and products.");
        }
        this.orderId = orderId;
        this.customer = customer;
        this.products = new ArrayList<>(products);
        double total = 0;
        StringBuilder text = new StringBuilder();
        text.append("\n=== RECEIPT #").append(orderId).append(" ===\n");
        text.append("Customer: ").append(customer.getName()).append('\n');
        for (Product product : this.products) {
            if (product == null) {
                throw new IllegalArgumentException("An order must not contain a null product.");
            }
            total += product.getPrice();
            text.append(String.format(Locale.ROOT, "%s - %.2f KZT%n",
                    product.getName(), product.getPrice()));
        }
        if (!Double.isFinite(total)) {
            throw new IllegalArgumentException("Order total is too large.");
        }
        this.totalAmount = total;
        text.append(String.format(Locale.ROOT, "TOTAL: %.2f KZT%n", totalAmount));
        this.receipt = text.toString();
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public ArrayList<Product> getProducts() { return new ArrayList<>(products); }
    public double getTotalAmount() { return totalAmount; }
    public String printReceipt() { return receipt; }
}
