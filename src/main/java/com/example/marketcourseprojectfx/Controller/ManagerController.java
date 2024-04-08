package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.HelloApplication;
import com.example.marketcourseprojectfx.Model.Shop;
import com.example.marketcourseprojectfx.Model.Users;
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
        // Добавим проверку на null, чтобы избежать NullPointerException
        if (userData != null) {
            ShopTabDataLoad();
        } else {
            System.err.println("User data is null.");
        }
    }

    public void ShopTabDataLoad() {
        DbController db = new DbController();
        Shop shopTab = new Shop(db.getShopDataForUser(userData.getUsername()).getName(), db.getShopDataForUser(userData.getUsername()).getAddress(), db.getShopDataForUser(userData.getUsername()).getEmail());

        ShopNameField.setText(shopTab.getName());
        ShopAddressField.setText(shopTab.getAddress());
        ShopEmailField.setText(shopTab.getEmail());
    }

    public void setUserData(Users user) {
        this.userData = user;
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
    }

    // ProductTab
    public void loadProductData(MouseEvent mouseEvent) {
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
