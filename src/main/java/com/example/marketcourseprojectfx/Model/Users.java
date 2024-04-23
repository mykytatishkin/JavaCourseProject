package com.example.marketcourseprojectfx.Model;

public class Users {
    private int id;
    private String username;
    private String password;
    private String role;
    private Integer shopId; // Используем Integer для ShopId, чтобы он мог быть null

    public Users(int id, String username, String password, String role, int shopId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.shopId = shopId;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Integer getShopId() {
        return shopId;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}

