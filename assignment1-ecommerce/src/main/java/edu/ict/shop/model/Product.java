package edu.ict.shop.model;

import java.util.Locale;

public class Product {
    private final int id;
    private String name;
    private double price;
    private int stockQuantity;

    public Product(int id, String name, double price, int stockQuantity) {
        if (id <= 0) {
            throw new IllegalArgumentException("Product ID must be positive.");
        }
        this.id = id;
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    public final void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name must not be empty.");
        }
        this.name = name.trim();
    }

    public final void setPrice(double price) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Price must be a finite, non-negative number.");
        }
        this.price = price;
    }

    public final void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock must not be negative.");
        }
        this.stockQuantity = stockQuantity;
    }

    public String displayInfo() {
        return String.format(Locale.ROOT, "%d | %s | %.2f KZT | stock=%d",
                id, name, price, stockQuantity);
    }
}
