package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.HelloApplication;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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

    public void SignUp(ActionEvent actionEvent) {
        String username = UsernameSignUpField.getText();
        String password = PasswordSignUpField.getText();
        String confirmPassword = ConfirmPasswordSignUpField.getText();

        if (!password.equals(confirmPassword))
        {
            errorTitleSignUp.setText("Password does not match Confirm Password");
            return;
        }

        if (username.length() < 2)
        {
            errorTitleSignUp.setText("Username should contain at least two characters");
            return;
        }

        if (!AgreeWithPoliticsCheckBox.isSelected())
        {
            errorTitleSignUp.setText("Please agree with the politics");
            return;
        }
        // TODO: Validation of password
        if (password.length() < 1 || confirmPassword.length() < 1)
        {
            errorTitleSignUp.setText("Password should contain more than 1 character");
        }

        if (password != confirmPassword)
        {
            errorTitleSignUp.setText("Passwords do not match");
        }

        try
        {
            dbController.Connect();
            if (dbController.getUser(username) != null) {
                errorTitleSignUp.setText("User with this username already exists");
                return;
            }
            Users user = new Users(0, username, password, "user", 1); // По умолчанию роль "user" и пустой ShopId

            dbController.addUser(user);

            errorTitleSignUp.setText("User registered successfully");

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (SQLException e)
        {
            errorTitleSignUp.setText("Error occurred during user registration");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            dbController.Disconnect();
        }
    }

    public void HaveAnAccount(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}