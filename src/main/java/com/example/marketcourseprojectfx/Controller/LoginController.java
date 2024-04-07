package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public TextField UsernameField;
    public TextField PasswordField;
    public Button LoginButton;
    public Button SignUpButton;
    public Label ErrorTitle;

    public void Login(ActionEvent actionEvent) {
        DbController dbController = new DbController();
        dbController.Connect(); // Connect to the database

        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (username.length() > 1 && password.length() > 1) {
            boolean isValidCredentials = dbController.checkUserCredentials(username, password);
            if (isValidCredentials) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Admin.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Закройте предыдущее окно входа
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ErrorTitle.setText("Invalid Credentials");
            }
        } else {
            ErrorTitle.setText("Null Credentials");
        }

        dbController.Disconnect(); // Disconnect from the database
    }
}
