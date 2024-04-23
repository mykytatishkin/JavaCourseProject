package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.Extension.ChangePage;
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

import java.io.BufferedReader;
import java.io.FileReader;
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

    // Data Binding
    private Users userData;
    private ChangePage cp = new ChangePage();

    public void initialize() {
        setUserDataFromTextFile();
    }

    public void setUserDataFromTextFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            userData = new Users(); // Создаем новый объект Users для userData
            while ((line = reader.readLine()) != null) {
                // Разбиваем строку на части по ":"
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    switch (key) {
                        case "Username":
                            userData.setUsername(value);
                            break;
                        case "Role":
                            userData.setRole(value);
                            break;
                        case "ShopId":
                            userData.setShopId(Integer.parseInt(value));
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
    }
    public void UpdateShop(ActionEvent actionEvent) {
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        cp.ChangePage(actionEvent,"SignUp");
    }

    public void loadProductData(MouseEvent mouseEvent) {
    }

    public void AddProduct(ActionEvent actionEvent) {
    }

    public void UpdateProduct(ActionEvent actionEvent) {
    }

    public void DeleteProduct(ActionEvent actionEvent) {
    }

}
