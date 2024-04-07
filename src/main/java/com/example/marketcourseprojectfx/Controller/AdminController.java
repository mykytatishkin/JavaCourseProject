package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.Model.Product;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {
    // Shop
    public ListView ShopList;
    public Button DeleteShopButton;
    public Button UpdateShopButton;
    public Button CreateShopButton;
    public TextField ShopAddressField;
    public TextField ShopNameField;
    public TextField ShopEmailField;
    // Product
    public ListView ProductListAdmin;
    public TextField ProductTitleField;
    public TextArea ProductDescriptionField;
    public TextField ProductQuantityField;
    public TextField ProductPriceField;
    public TextField OwnerIdField;
    // Users
    public TextField LoginField;
    public TextField PasswordField;
    public Button CreateButton;
    public Button UpdateButton;
    public Button DeleteButton;
    public Button LoadButton;
    public ListView ListViewUsersAdmin;
    public TextField RoleField;
    public TextField ShopId;

    private Product selectedProduct; // Добавляем поле для хранения выбранного продукта

    private final DbController dbController = new DbController();

    public void initialize() {
        dbController.Connect();
        loadShopData();
        loadProductData();
        loadUserData();

        ListViewUsersAdmin.setOnMouseClicked(event -> {
            String selectedItem = (String) ListViewUsersAdmin.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String[] parts = selectedItem.split(",");
                for (String part : parts) {
                    String[] keyValue = part.trim().split(":");
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    switch (key) {
                        case "Id":
                            break;
                        case "Username":
                            LoginField.setText(value);
                            break;
                        case "Password":
                            PasswordField.setText(value);
                            break;
                        case "Role":
                            RoleField.setText(value);
                            break;
                        case "ShopId":
                            ShopId.setText(value);
                            break;
                    }
                }
            }
        });

        ProductListAdmin.setOnMouseClicked(event -> {
            String selectedItem = (String) ProductListAdmin.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String[] parts = selectedItem.split(",");
                for (String part : parts) {
                    String[] keyValue = part.trim().split(":");
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    switch (key) {
                        case "Name":
                            ProductTitleField.setText(value);
                            break;
                        case "Description":
                            ProductDescriptionField.setText(value);
                            break;
                        case "Price":
                            ProductPriceField.setText(value);
                            break;
                        case "Quantity":
                            ProductQuantityField.setText(value);
                            break;
                        case "OwnerId":
                            OwnerIdField.setText(value);
                            break;
                    }
                }
            }
        });

    }

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
        }
    }

    public void CreateShop(ActionEvent actionEvent) {
    }

    public void UpdateShop(ActionEvent actionEvent) {
    }

    public void DeleteShop(ActionEvent actionEvent) {
    }

    public void loadProductData() {
        ObservableList<String> productItems = FXCollections.observableArrayList();
        ResultSet resultSet = dbController.getAllProducts();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String productName = resultSet.getString("Name");
                String productDescription = resultSet.getString("Description");
                double productPrice = resultSet.getDouble("Price");
                int productQuantity = resultSet.getInt("Quantity");
                int ownerId = resultSet.getInt("OwnerId");

                String productString = "Id: " + id + ", Name: " + productName + ", Description: " + productDescription +
                        ", Price: " + productPrice + ", Quantity: " + productQuantity + ", OwnerId: " + ownerId;

                productItems.add(productString);
            }
            ProductListAdmin.setItems(productItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void AddProduct(ActionEvent actionEvent) {
        String productName = ProductTitleField.getText();
        String productDescription = ProductDescriptionField.getText();
        int productQuantity = Integer.parseInt(ProductQuantityField.getText());
        int ownerId = Integer.parseInt(OwnerIdField.getText());

        Product product = new Product(productName, productDescription, productQuantity, ownerId);
        dbController.addProduct(product);

        ProductTitleField.clear();
        ProductDescriptionField.clear();
        ProductQuantityField.clear();
        OwnerIdField.clear();

        loadProductData();
    }

    // TODO: Fix update func
    public void UpdateProduct(ActionEvent actionEvent) {
        // Получаем выбранный продукт из ListView
        String selectedItem = (String) ProductListAdmin.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Разбиваем строку на части, разделенные запятой
            String[] parts = selectedItem.split(",");
            String name = null;
            String description = null;
            int quantity = 0;
            int ownerId = 0;
            for (String part : parts) {
                String[] keyValue = part.trim().split(":");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                switch (key) {
                    case "Name":
                        name = value;
                        break;
                    case "Description":
                        description = value;
                        break;
                    case "Quantity":
                        quantity = Integer.parseInt(value);
                        break;
                    case "OwnerId":
                        ownerId = Integer.parseInt(value);
                        break;
                }
            }
            Product product = new Product(name, description, quantity, ownerId);
            dbController.updateProduct(product);
            loadProductData();
        }
    }





    public void DeleteProduct(ActionEvent actionEvent) {
        String selectedItem = (String) ProductListAdmin.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Разбиваем строку на части, разделенные запятой
            String[] parts = selectedItem.split(",");
            // Инициализируем переменную для хранения id продукта
            int productId = 0;
            // Извлекаем id продукта из строки
            for (String part : parts) {
                // Разбиваем часть на ключ и значение
                String[] keyValue = part.trim().split(":");
                // Извлекаем имя параметра и его значение
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                // Если ключ - это Id, то присваиваем значение переменной productId
                if (key.equals("Id")) {
                    productId = Integer.parseInt(value);
                    break;
                }
            }
            // Удаляем продукт с заданным id
            dbController.deleteProduct(productId);
            loadProductData();
        }
    }

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
            ListViewUsersAdmin.setItems(userItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void LoadDataButton(ActionEvent actionEvent) {
        loadUserData();
    }

    public void CreateButton(ActionEvent actionEvent) {
        String username = LoginField.getText();
        String password = PasswordField.getText();
        String role = RoleField.getText();
        int shopId = Integer.parseInt(ShopId.getText());

        Users user = new Users(username, password, role, shopId);
        dbController.createUser(user);
        loadUserData();
    }

    public void UpdateButton(ActionEvent actionEvent) {
        String selectedItem = (String) ListViewUsersAdmin.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] parts = selectedItem.split(",");
            int id = -1;
            for (String part : parts) {
                String[] keyValue = part.trim().split(":");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                if (key.equals("Id")) {
                    id = Integer.parseInt(value);
                    break;
                }
            }
            if (id != -1) {
                String username = LoginField.getText();
                String password = PasswordField.getText();
                String role = RoleField.getText();
                int shopId = Integer.parseInt(ShopId.getText());

                Users user = new Users(id, username, password, role, shopId);
                dbController.updateUser(user);
                loadUserData();
            }
        }
    }

    public void DeleteButton(ActionEvent actionEvent) {
        String selectedItem = (String) ListViewUsersAdmin.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] parts = selectedItem.split(",");
            int id = -1;
            for (String part : parts) {
                String[] keyValue = part.trim().split(":");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                if (key.equals("Id")) {
                    id = Integer.parseInt(value);
                    break;
                }
            }
            if (id != -1) {
                dbController.deleteUser(id);
                loadUserData();
            }
        }
    }
}