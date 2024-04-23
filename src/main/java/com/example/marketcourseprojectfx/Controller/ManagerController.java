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

    private ChangePage cp = new ChangePage();

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
