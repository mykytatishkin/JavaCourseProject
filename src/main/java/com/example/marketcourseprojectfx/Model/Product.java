package com.example.marketcourseprojectfx.Model;

public class Product {
    public int Id;
    public String Name;
    public String Description;
    public double Price;
    public int Quantity;
    public int OwnerId;

    public Product(int id, String name, String description, double price, int quantity, int ownerId)
    {
        Id = id;
        Name = name;
        Description = description;
        Price = price;
        Quantity = quantity;
        OwnerId = ownerId;
    }
}
