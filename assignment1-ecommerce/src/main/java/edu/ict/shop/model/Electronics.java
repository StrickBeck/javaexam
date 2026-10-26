package edu.ict.shop.model;

public class Electronics extends Product {
    private String brand;

    public Electronics(int id, String name, double price, int stockQuantity, String brand) {
        super(id, name, price, stockQuantity);
        setBrand(brand);
    }

    public String getBrand() { return brand; }

    public final void setBrand(String brand) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand must not be empty.");
        }
        this.brand = brand.trim();
    }

    @Override
    public String displayInfo() {
        return "Electronics | " + super.displayInfo() + " | brand=" + brand;
    }
}
