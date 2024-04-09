package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.HelloApplication;
import com.example.marketcourseprojectfx.Model.Shop;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerController {

    // ShopTab
    public TextField ShopAddressField;
    public TextField ShopNameField;
    public TextField ShopEmailField;
    // ProductTab
    public ListView ProductListAdmin;
    public TextField ProductTitleField;
    public TextArea ProductDescriptionField;
    public TextField ProductQuantityField;
    public TextField OwnerIdField;
    public TextField ProductPriceField;
    // ProfileTab
    public TextField PasswordField;
    public TextField ConfirmPasswordField;
    public Label UsernameLabel;
    public TextField FirstNameField;
    public TextField LastNameField;
    public TextField EmailField;
    public TextField PhoneField;

    // Data Binding
    private Users userData;

    public void initialize() {
        ShopTabDataLoad();
    }

    public void ShopTabDataLoad() {
        if (userData != null) {
            DbController db = new DbController();
            Shop shopTab = db.getShopDataForUser(userData.getUsername());

            if (shopTab != null) {
                ShopNameField.setText(shopTab.getName());
                ShopAddressField.setText(shopTab.getAddress());
                ShopEmailField.setText(shopTab.getEmail());
            } else {
                System.out.println("User not found");
            }
        } else {
            System.out.println("UserData not found");
        }
    }

    public void setUserData(Users user) {
        if (user != null) {
            this.userData = user;
        }
        else if (user == null) {
            System.err.println("User data is null.");
        }
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    // ShopTab
    public void UpdateShop(ActionEvent actionEvent) {
        String newShopName = ShopNameField.getText();
        String newShopAddress = ShopAddressField.getText();
        String newShopEmail = ShopEmailField.getText();

        if (newShopName.isEmpty() || newShopAddress.isEmpty() || newShopEmail.isEmpty()) {
            // Проверка на пустые поля, возможно, нужно дополнительное сообщение об ошибке
            return;
        }

        DbController dbController = new DbController();
        Shop currentShop = dbController.getShopDataForUser(userData.getUsername()); // Получаем текущие данные магазина из базы данных

        if (currentShop != null) {
            // Обновляем поля магазина
            currentShop.setName(newShopName);
            currentShop.setAddress(newShopAddress);
            currentShop.setEmail(newShopEmail);

            // Выполняем обновление данных магазина в базе данных
            dbController.updateShop(currentShop);

            // Можно добавить сообщение об успешном обновлении данных магазина
            System.out.println("Shop data updated successfully.");
        } else {
            // Можно добавить сообщение об ошибке, если текущие данные магазина не найдены
            System.out.println("Failed to update shop data: shop data not found.");
        }
    }

    // ProductTab
    public void loadProductData(MouseEvent mouseEvent) {
        if (userData != null) { // Проверяем, что пользователь не null
            DbController dbController = new DbController();
            ObservableList<String> productItems = FXCollections.observableArrayList();
            int ownerId = userData.getShopId(); // Получаем ownerId из userData
            ResultSet resultSet = dbController.getAllProductsByOwnerId(ownerId); // Передаем ownerId
            try {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String productName = resultSet.getString("Name");
                    String productDescription = resultSet.getString("Description");
                    double productPrice = resultSet.getDouble("Price");
                    int productQuantity = resultSet.getInt("Quantity");
                    int productOwnerId = resultSet.getInt("OwnerId"); // Мы получаем владельца для проверки, но это необязательно

                    String productString = "Id: " + id + ", Name: " + productName + ", Description: " + productDescription +
                            ", Price: " + productPrice + ", Quantity: " + productQuantity + ", OwnerId: " + productOwnerId;

                    productItems.add(productString);
                }
                ProductListAdmin.setItems(productItems);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User data is null. Cannot load product data.");
        }
    }

    public void AddProduct(ActionEvent actionEvent) {
    }

    public void UpdateProduct(ActionEvent actionEvent) {
    }

    public void DeleteProduct(ActionEvent actionEvent) {
    }

    // ProfileTab
    public void UpdateProfile(ActionEvent actionEvent) {

    }
}
