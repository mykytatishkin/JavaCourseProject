package com.example.marketcourseprojectfx.Controller;
import com.example.marketcourseprojectfx.Extension.ChangePage;
import com.example.marketcourseprojectfx.Model.Users;

import java.sql.*;

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
}