package edu.ict.shop.model;

public class Clothing extends Product {
    private String size;

    public Clothing(int id, String name, double price, int stockQuantity, String size) {
        super(id, name, price, stockQuantity);
        setSize(size);
    }

    public String getSize() { return size; }

    public final void setSize(String size) {
        if (size == null || size.isBlank()) {
            throw new IllegalArgumentException("Size must not be empty.");
        }
        this.size = size.trim();
    }

    @Override
    public String displayInfo() {
        return "Clothing | " + super.displayInfo() + " | size=" + size;
    }
}
