package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.Extension.ChangePage;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.io.IOException;

public class SignUpController
{
    public TextField UsernameSignUpField;
    public PasswordField PasswordSignUpField;
    public PasswordField ConfirmPasswordSignUpField;
    public Button SignUpButton;
    public CheckBox AgreeWithPoliticsCheckBox;
    public Label errorTitleSignUp;
    public Button HaveAnAccount;

    private DbController dbController = new DbController();
    private ChangePage cp = new ChangePage();

    public void SignUp(ActionEvent actionEvent) throws IOException {
        String username = UsernameSignUpField.getText();
        String password = PasswordSignUpField.getText();
        String confirmPassword = ConfirmPasswordSignUpField.getText();

        // Проверка, что пароль и его подтверждение совпадают
        if (!password.equals(confirmPassword)) {
            errorTitleSignUp.setText("Passwords don't match");
            return;
        }

        // Проверка, что пользователь с таким именем не существует
        if (dbController.GetUser(username, password) != null) {
            errorTitleSignUp.setText("User already exists");
            return;
        }

        // Проверка, что чекбокс "Согласен с политикой" отмечен
        if (!AgreeWithPoliticsCheckBox.isSelected()) {
            errorTitleSignUp.setText("You must agree with the politics");
            return;
        }

        // Регистрация нового пользователя
        if (dbController.RegisterUser(username, password)) {
            errorTitleSignUp.setText("Registration successful");
            // Опционально: переход на страницу входа после успешной регистрации
            cp.ChangePage(actionEvent, "Login");
        } else {
            errorTitleSignUp.setText("Error registering user");
        }
    }

    public void HaveAnAccount(ActionEvent actionEvent) throws IOException
    {
        cp.ChangePage(actionEvent,"Login");
    }
}