package com.example.marketcourseprojectfx.Controller;
import com.example.marketcourseprojectfx.Extension.ChangePage;
import com.example.marketcourseprojectfx.Model.Product;
import com.example.marketcourseprojectfx.Model.Shop;
import com.example.marketcourseprojectfx.Model.Users;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbController {
    private String connectionUrl =
            "jdbc:sqlserver://localhost:1433;"
                    + "database=CourseProject;"
                    + "user=sa;"
                    + "password=Password.1;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";
    private Connection connection;

    public void Connect() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public void Disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            System.err.println("Error while disconnecting from the database: " + e.getMessage());
        }
    }

    public Users GetUser(String username, String password) {
        System.out.println("Getting user: " + username + " and password: " + password);
        Users user = null;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String role = resultSet.getString("role");
                    Integer shopId = resultSet.getInt("shopId");

                    // Assuming you have other fields in your Users model, retrieve them similarly

                    // Create a new Users object
                    user = new Users(id, username, password, role, shopId);
                    System.out.println("User: " + user.getId() + " " + user.getUsername() + " " + user.getPassword());

                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user from the database: " + e.getMessage());
        }

        return user;
    }
    public boolean RegisterUser(String username, String password) {
        String query = "INSERT INTO users (username, password, role, shopId) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Устанавливаем значения параметров запроса
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "user"); // Роль по умолчанию
            statement.setInt(4, 1); // ShopId по умолчанию

            // Выполняем запрос на добавление пользователя
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Возвращаем true, если добавление прошло успешно
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public List<Shop> GetAllShops() {
        String query = "SELECT * FROM Shop";
        List<Shop> shops = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");

                // Create a new Shop object
                Shop shop = new Shop(name, address, email);
                shops.add(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shops;
    }
    public List<Shop> GetAllShopsOrderedById() {
        String query = "SELECT * FROM Shop order by Id";
        List<Shop> shops = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");

                // Create a new Shop object
                Shop shop = new Shop(name, address, email);
                shops.add(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shops;
    }
    public List<Shop> GetAllShopsOrderedByAddress() {
        String query = "SELECT * FROM Shop order by Address";
        List<Shop> shops = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");

                // Create a new Shop object
                Shop shop = new Shop(name, address, email);
                shops.add(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shops;
    }
    public List<Shop> GetAllShopsOrderedByEmail() {
        String query = "SELECT * FROM Shop order by Email";
        List<Shop> shops = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");

                // Create a new Shop object
                Shop shop = new Shop(name, address, email);
                shops.add(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shops;
    }
    public List<Product> GetProductsForShop(String shopName) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE OwnerId IN (SELECT id FROM shop WHERE name = ?)";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Установка параметра для имени магазина
            statement.setString(1, shopName);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Получение данных о продуктах из результата запроса
                    int id = resultSet.getInt("Id");
                    String name = resultSet.getString("Name");
                    String description = resultSet.getString("Description");
                    int quantity = resultSet.getInt("Quantity");
                    int ownerId = resultSet.getInt("OwnerId");

                    // Создание объекта Product и добавление его в список
                    Product product = new Product(name, description, quantity, ownerId);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    public Product GetProductByName(String productName) {
        // Запрос к базе данных для получения продукта по его имени
        String query = "SELECT * FROM product WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Устанавливаем параметры запроса
            statement.setString(1, productName);

            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery();

            // Проверяем, есть ли результат
            if (resultSet.next()) {
                // Если есть, создаем объект Product и заполняем его данными из базы данных
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int quantity = resultSet.getInt("quantity");
                int ownerId = resultSet.getInt("OwnerId");

                return new Product(id, name, description, quantity, ownerId);
            } else {
                // Если продукт с указанным именем не найден, возвращаем null
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting product by name: " + e.getMessage());
            return null;
        }
    }
    public void UpdateProductQuantity(int productId, int newQuantity) {
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?")) {
            statement.setInt(1, newQuantity);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating product quantity: " + e.getMessage());
        }
    }
}