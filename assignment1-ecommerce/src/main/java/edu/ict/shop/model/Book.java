package edu.ict.shop.model;

public class Book extends Product {
    private String author;

    public Book(int id, String name, double price, int stockQuantity, String author) {
        super(id, name, price, stockQuantity);
        setAuthor(author);
    }

    public String getAuthor() { return author; }

    public final void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author must not be empty.");
        }
        this.author = author.trim();
    }

    @Override
    public String displayInfo() {
        return "Book | " + super.displayInfo() + " | author=" + author;
    }
}
