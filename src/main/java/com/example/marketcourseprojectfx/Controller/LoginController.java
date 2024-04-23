package com.example.marketcourseprojectfx.Controller;
import com.example.marketcourseprojectfx.Extension.ChangePage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Locale;

public class LoginController {
    public TextField UsernameField;
    public TextField PasswordField;
    public Button LoginButton;
    public Button SignUpButton;
    public Label ErrorTitle;

    private DbController dbController = new DbController();
    private ChangePage cp = new ChangePage();

    public void initialize() {
        dbController.Connect();
    }

    public void Login(ActionEvent actionEvent) throws IOException {
        var authResult = dbController.GetUser(UsernameField.getText(), PasswordField.getText());

        if (authResult != null) {
            switch (authResult.getRole().toLowerCase(Locale.ROOT)) {
                case "admin":
                    cp.ChangePage(actionEvent, "SignUp");
                    break;
                case "user":
                    cp.ChangePage(actionEvent, "User");
                    break;
                case "manager":
                    cp.ChangePage(actionEvent, "Manager");
                    break;
                default:
                    System.out.println(authResult.getRole());
                    break;
            }
        } else {
            System.out.println("Authentication failed.");
        }
    }

    public void SignUp(ActionEvent actionEvent) throws IOException {
        cp.ChangePage(actionEvent,"SignUp");
    }
}

