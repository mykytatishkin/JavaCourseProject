package com.example.marketcourseprojectfx.Model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int ownerId;

    public Product(int productId, String productName, String productDescription, int productQuantity, int ownerId) {
        this.id = productId;
        this.name = productName;
        this.description = productDescription;
        this.quantity = productQuantity;
        this.ownerId = ownerId;
    }

    public Product(String productName, String productDescription, int productQuantity, int ownerId) {
        this.name = productName;
        this.description = productDescription;
        this.quantity = productQuantity;
        this.ownerId = ownerId;
    }

    // Геттеры и сеттеры для всех полей

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
