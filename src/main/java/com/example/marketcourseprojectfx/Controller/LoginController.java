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
import com.example.marketcourseprojectfx.Model.Users;
import java.io.IOException;

public class LoginController {
    public TextField UsernameField;
    public TextField PasswordField;
    public Button LoginButton;
    public Button SignUpButton;
    public Label ErrorTitle;
    private DbController dbController = new DbController();

    public void initialize() {
        dbController.Connect();
    }

    public void Login(ActionEvent actionEvent) {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (username.length() > 1 && password.length() > 1) {
            Users user = dbController.getUser(username, password);

            if (user != null) {
                String role = user.getRole();
                String fxmlPath = "";

                switch (role) {
                    case "admin":
                        fxmlPath = "Admin.fxml";
                        break;
                    case "manager":
                        fxmlPath = "Manager.fxml";
                        break;
                    case "user":
                        fxmlPath = "User.fxml";
                        break;
                    default:
                        ErrorTitle.setText("Invalid Role");
                        return;
                }

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

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
    }

    public void SignUp(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SignUp.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

