package com.example.marketcourseprojectfx.Model;

public class Users {
    private int id;
    private String username;
    private String password;
    private String role;
    private Integer shopId; // Используем Integer для ShopId, чтобы он мог быть null

    public Users(String username, String password, String role, int shopId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.shopId = shopId;
    }
    public Users(int id, String username, String password, String role, Integer shopId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.shopId = shopId;
    }

    // Геттеры и сеттеры для всех полей
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}