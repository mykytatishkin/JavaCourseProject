package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {
    // ShopTab
    public ListView ShopList;
    public Button DeleteShopButton;
    public Button UpdateShopButton;
    public Button CreateShopButton;
    public TextField ShopAddressField;
    public TextField ShopNameField;
    public TextField ShopEmailField;
    // ProductTab
    public ListView ProductListAdmin;
    public TextField ProductTitleField;
    public TextArea ProductDescriptionField;
    public TextField ProductQuantityField;
    public TextField ProductWeightField;
    // UserTab
    public TextField LoginField;
    public TextField PasswordField;
    public Button CreateButton;
    public Button UpdateButton;
    public Button DeleteButton;
    public Button LoadButton;
    public ListView ListViewUsersAdmin;
    public TextField RoleField;
    public TextField ShopId;
    private final DbController dbController = new DbController();



    public void initialize() {
        dbController.Connect(); // Подключаемся к базе данных при инициализации контроллера
        loadShopData();
        loadProductData();
        loadUserData();
    }


    // ShopTab
    private void loadShopData() {
        ObservableList<String> shopItems = FXCollections.observableArrayList();
        ResultSet resultSet = dbController.getAllShops();
        try {
            while (resultSet.next()) {
                String shopName = resultSet.getString("Name");
                shopItems.add(shopName);
            }
            ShopList.setItems(shopItems);
        } catch (SQLException e) {
            e.printStackTrace();
            // Можно добавить отображение сообщения об ошибке пользователю
        }
    }

    public void CreateShop(ActionEvent actionEvent) {
    }
    public void UpdateShop(ActionEvent actionEvent) {
    }
    public void DeleteShop(ActionEvent actionEvent) {
    }


    // ProductTab
    public void loadProductData() {
        ObservableList<String> productItems = FXCollections.observableArrayList();
        ResultSet resultSet = dbController.getAllProducts();
        try {
            while (resultSet.next()) {
                String productName = resultSet.getString("Name");
                productItems.add(productName);
            }
            ProductListAdmin.setItems(productItems);
        } catch (SQLException e) {
            e.printStackTrace();
            // Можно добавить отображение сообщения об ошибке пользователю
        }
    }

    public void AddProduct(ActionEvent actionEvent) {
    }

    public void UpdateProduct(ActionEvent actionEvent) {
    }

    public void DeleteProduct(ActionEvent actionEvent) {
    }

    // UserTab
    private void loadUserData() {
        ObservableList<String> userItems = FXCollections.observableArrayList();
        ResultSet resultSet = dbController.getAllUsers();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String role = resultSet.getString("Role");
                int shopId = resultSet.getInt("ShopId");

                String userString = "Id: " + id + ", Username: " + username + ", Password: " + password + ", Role: " + role + ", ShopId: " + shopId;
                userItems.add(userString);
            }
            // Устанавливаем список пользователей в ListView
            // ShopList.setItems(userItems); // Необходимо заменить на ListView для пользователей
            ListViewUsersAdmin.setItems(userItems);
        } catch (SQLException e) {
            e.printStackTrace();
            // Можно добавить отображение сообщения об ошибке пользователю
        }
    }

    public void LoadDataButton(ActionEvent actionEvent) {
        System.out.println("Load Button Clicked");
        loadUserData();
    }

    public void CreateButton(ActionEvent actionEvent) {
    }

    public void UpdateButton(ActionEvent actionEvent) {
    }

    public void DeleteButton(ActionEvent actionEvent) {
    }



}
