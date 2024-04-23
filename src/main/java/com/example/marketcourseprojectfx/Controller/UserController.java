package com.example.marketcourseprojectfx.Controller;
import com.example.marketcourseprojectfx.Extension.ChangePage;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserController {

    public ListView ShopList;
    public ListView ProductList;
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

    public void LogOut(ActionEvent actionEvent) throws IOException {
        cp.ChangePage(actionEvent,"LogIn");
    }

    public void BuyItem(ActionEvent actionEvent) {
    }

    public void RateItem(ActionEvent actionEvent) {
    }

    public void CommentItem(ActionEvent actionEvent) {
    }
}
