package edu.ict.shop.model;

public class User {
    private final int id;
    private String name;
    private String email;
    private String password;
    private final String role;

    public User(int id, String name, String email, String password, String role) {
        if (id <= 0) {
            throw new IllegalArgumentException("User ID must be positive.");
        }
        this.id = id;
        setName(name);
        setEmail(email);
        setPassword(password);
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public final void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty.");
        }
        this.name = name.trim();
    }

    public final void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @.");
        }
        this.email = email.trim();
    }

    public final void setPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must have at least 4 characters.");
        }
        this.password = password;
    }

    public boolean checkPassword(String enteredPassword) {
        return password.equals(enteredPassword);
    }

    @Override
    public String toString() {
        return id + " | " + role + " | " + name + " | " + email;
    }
}
