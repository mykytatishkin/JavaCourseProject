package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.HelloApplication;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserController {

    // Data Binding
    private Users userData;

    public void setUserData(Users user) {
        this.userData = user;
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Закройте предыдущее окно входа
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
