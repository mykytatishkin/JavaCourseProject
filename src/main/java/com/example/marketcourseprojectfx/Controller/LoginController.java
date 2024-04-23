package com.example.marketcourseprojectfx.Controller;
import com.example.marketcourseprojectfx.Extension.ChangePage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Locale;
import java.io.FileWriter;

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
            // Очистка файла перед записью
            try (FileWriter writer = new FileWriter("user.txt", false)) {
                writer.write(""); // Очищаем содержимое файла
            } catch (IOException e) {
                System.err.println("Error clearing user file: " + e.getMessage());
            }
            // Запись данных пользователя в файл user.txt
            try (FileWriter writer = new FileWriter("user.txt")) {
                writer.write("Username: " + authResult.getUsername() + "\n");
                writer.write("Role: " + authResult.getRole() + "\n");
                writer.write("ShopId: " + authResult.getShopId() + "\n");
            } catch (IOException e) {
                System.err.println("Error writing user data to file: " + e.getMessage());
            }
        } else {
            System.out.println("Authentication failed.");
        }
    }

    public void SignUp(ActionEvent actionEvent) throws IOException {
        cp.ChangePage(actionEvent,"LogIn");
    }
}

