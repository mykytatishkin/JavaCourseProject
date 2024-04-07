package com.example.marketcourseprojectfx.Controller;

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

    public boolean checkUserCredentials(String username, String password) {
        boolean isValid = false;
        String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while checking user credentials: " + e.getMessage());
        }

        return isValid;
    }

    public Users getUser(String username, String password) {
        Users user = null;
        String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Users(
                            resultSet.getInt("Id"),
                            resultSet.getString("Username"),
                            resultSet.getString("Password"),
                            resultSet.getString("Role"),
                            resultSet.getInt("ShopId")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching user from the database: " + e.getMessage());
        }
        return user;
    }
    public Users getUser(String username) {
        Users user = null;
        String sql = "SELECT * FROM Users WHERE Username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Users(
                            resultSet.getInt("Id"),
                            resultSet.getString("Username"),
                            resultSet.getString("Password"),
                            resultSet.getString("Role"),
                            resultSet.getInt("ShopId")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching user from the database: " + e.getMessage());
        }

        return user;
    }
    public void addUser(Users user) throws SQLException {
        String sql = "INSERT INTO Users (Username, Password, Role, ShopId) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getShopId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding user to the database: " + e.getMessage());
            throw e; // Пробрасываем исключение дальше для обработки в контроллере
        }
    }
    public ResultSet getAllShops() {
        ResultSet resultSet = null;
        String sql = "SELECT Name FROM Shop";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching shop data: " + e.getMessage());
        }

        return resultSet;
    }

    public ResultSet getAllProducts() {
        ResultSet resultSet = null;
        String sql = "SELECT Name FROM Product";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching product data: " + e.getMessage());
        }

        return resultSet;
    }

    public ResultSet getAllUsers() {
        ResultSet resultSet = null;
        String sql = "SELECT Username FROM Users";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching user data: " + e.getMessage());
        }

        return resultSet;
    }
}