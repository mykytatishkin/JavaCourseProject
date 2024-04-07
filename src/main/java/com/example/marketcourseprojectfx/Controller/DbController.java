package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.Model.Product;
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
        String sql = "SELECT * FROM Shop";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching shop data: " + e.getMessage());
        }

        return resultSet;
    }
    public void createShop(String shopName, String shopAddress, String shopEmail) {
        String sql = "INSERT INTO Shop (Name, Address, Email) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, shopName);
            statement.setString(2, shopAddress);
            statement.setString(3, shopEmail);

            statement.executeUpdate();
            System.out.println("Shop created successfully.");
        } catch (SQLException e) {
            System.err.println("Error while creating shop: " + e.getMessage());
        }
    }
    public void updateShop(String shopName, String shopAddress, String shopEmail, String selectedShopName) {
        String sql = "UPDATE Shop SET Name=?, Address=?, Email=? WHERE Name=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, shopName);
            statement.setString(2, shopAddress);
            statement.setString(3, shopEmail);
            statement.setString(4, selectedShopName); // Указываем старое название магазина для поиска

            statement.executeUpdate();
            System.out.println("Shop updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error while updating shop: " + e.getMessage());
        }
    }
    public void deleteShop(String shopName) {
        String sql = "DELETE FROM Shop WHERE Name=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, shopName);

            statement.executeUpdate();
            System.out.println("Shop deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error while deleting shop: " + e.getMessage());
        }
    }

    public ResultSet getAllProducts() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM Product"; // Выбираем все поля из таблицы "Product"
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching product data: " + e.getMessage());
        }

        return resultSet;
    }
    public void addProduct(Product product) {
        String sql = "INSERT INTO Product (Name, Description, Price, Quantity, OwnerId) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, product.getOwnerId());

            statement.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            System.err.println("Error while adding product to the database: " + e.getMessage());
        }
    }
    public void updateProduct(Product product) {
        String sql = "UPDATE Product SET Name=?, Description=?, Quantity=?, OwnerId=? WHERE Id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, product.getOwnerId());
            statement.setInt(5, product.getId());

            statement.executeUpdate();
            System.out.println("Product updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error while updating product: " + e.getMessage());
        }
    }
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM Product WHERE Id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            statement.executeUpdate();
            System.out.println("Product deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error while deleting product: " + e.getMessage());
        }
    }

    public ResultSet getAllUsers() {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM Users";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching user data: " + e.getMessage());
        }

        return resultSet;
    }
    public void createUser(Users user) {
        String sql = "INSERT INTO Users (Username, Password, Role, ShopId) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getShopId());

            statement.executeUpdate();
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            System.err.println("Error while creating user: " + e.getMessage());
        }
    }
    public void updateUser(Users user) {
        String sql = "UPDATE Users SET Username=?, Password=?, Role=?, ShopId=? WHERE Id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setInt(4, user.getShopId());
            statement.setInt(5, user.getId());

            statement.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error while updating user: " + e.getMessage());
        }
    }
    public void deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE Id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User with ID " + userId + " has been deleted successfully.");
            } else {
                System.out.println("No user found with ID " + userId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting user: " + e.getMessage());
        }
    }
}