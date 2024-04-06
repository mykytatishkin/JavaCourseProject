package com.example.marketcourseprojectfx.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbController {
    private String connectionUrl =
            "jdbc:sqlserver://localhost:1433;"
                    + "database=CourseProject;"
                    + "user=sa;"
                    + "password=Password.1;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";
    private Connection connection; // Добавлено поле для хранения соединения

    // Метод для подключения к базе данных
    public void Connect() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    // Метод для отключения от базы данных
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
}
