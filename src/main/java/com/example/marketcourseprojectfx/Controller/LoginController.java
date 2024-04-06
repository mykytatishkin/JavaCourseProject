package com.example.marketcourseprojectfx.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    public TextField UsernameField;
    public TextField PasswordField;
    public Button LoginButton;
    public Button SignUpButton;
    public Label ErrorTitle;

    public void Login(ActionEvent actionEvent)
    {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if(username.length() > 1 && password.length() > 1)
        {

        }
        else
        {
            ErrorTitle.setText("Invalid Credentials");
        }
    }
}
