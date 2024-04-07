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


        ListViewUsersAdmin.setOnMouseClicked(event -> {
            // Получаем выбранную запись
            String selectedItem = (String) ListViewUsersAdmin.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Разбиваем строку на части, разделенные запятой
                String[] parts = selectedItem.split(",");
                // Устанавливаем значения из частей строки в текстовые поля
                for (String part : parts) {
                    // Разбиваем часть на ключ и значение
                    String[] keyValue = part.trim().split(":");
                    // Извлекаем имя параметра и его значение
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    // Устанавливаем значение в соответствующее текстовое поле
                    switch (key) {
                        case "Id":
                            // Пропускаем, если это Id, так как мы не хотим его редактировать
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
    // TODO: Add role as choose from UserType
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
        String username = LoginField.getText();
        String password = PasswordField.getText();
        String role = RoleField.getText();
        int shopId = Integer.parseInt(ShopId.getText());

        // Создаем объект пользователя без указания id
        Users user = new Users(username, password, role, shopId);
        // Вызываем метод создания пользователя в контроллере базы данных
        dbController.createUser(user);
        // После создания пользователя обновляем список пользователей
        loadUserData();
    }



    public void UpdateButton(ActionEvent actionEvent) {
        // Получаем выбранную запись
        String selectedItem = (String) ListViewUsersAdmin.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Разбиваем строку на части, разделенные запятой
            String[] parts = selectedItem.split(",");
            int id = -1; // Идентификатор пользователя
            for (String part : parts) {
                // Разбиваем часть на ключ и значение
                String[] keyValue = part.trim().split(":");
                // Извлекаем имя параметра и его значение
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                // Если ключ - "Id", получаем значение идентификатора пользователя
                if (key.equals("Id")) {
                    id = Integer.parseInt(value);
                    break;
                }
            }
            // Если удалось получить идентификатор пользователя, обновляем его данные в базе данных
            if (id != -1) {
                String username = LoginField.getText();
                String password = PasswordField.getText();
                String role = RoleField.getText();
                int shopId = Integer.parseInt(ShopId.getText());

                // Создаем объект пользователя с новыми данными
                Users user = new Users(id, username, password, role, shopId);
                // Обновляем данные пользователя в базе данных
                dbController.updateUser(user);
                // После обновления данных обновляем список пользователей
                loadUserData();
            }
        }
    }

    public void DeleteButton(ActionEvent actionEvent) {
        // Получаем выбранную запись из ListView
        String selectedItem = (String) ListViewUsersAdmin.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Разбиваем строку на части, разделенные запятой
            String[] parts = selectedItem.split(",");
            int id = -1; // Идентификатор пользователя
            for (String part : parts) {
                // Разбиваем часть на ключ и значение
                String[] keyValue = part.trim().split(":");
                // Извлекаем имя параметра и его значение
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                // Если ключ - "Id", получаем значение идентификатора пользователя
                if (key.equals("Id")) {
                    id = Integer.parseInt(value);
                    break;
                }
            }
            // Если удалось получить идентификатор пользователя, удаляем его из базы данных
            if (id != -1) {
                // Удаляем пользователя из базы данных
                dbController.deleteUser(id);
                // После удаления обновляем список пользователей
                loadUserData();
            }
        }
    }




}
