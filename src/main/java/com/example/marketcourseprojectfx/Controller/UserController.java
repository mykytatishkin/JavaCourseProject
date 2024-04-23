package com.example.marketcourseprojectfx.Controller;
import com.example.marketcourseprojectfx.Extension.ChangePage;
import com.example.marketcourseprojectfx.Model.Product;
import com.example.marketcourseprojectfx.Model.Shop;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


public class UserController {

    public ListView ShopList;
    public ListView ProductList;
    // Data Binding
    private Users userData;
    private DbController db = new DbController();
    private ChangePage cp = new ChangePage();

    public void initialize() throws IOException {
        setUserDataFromTextFile();
        LoadShops();
        LoadProducts();
    }

    public void LoadShops() throws IOException {
        List<Shop> shops = db.GetAllShops();
        List<String> shopNames = new ArrayList<>();

        // Проход по списку магазинов и добавление их имен в список строк
        for (Shop shop : shops) {
            shopNames.add(shop.getName());
        }

        // Создание ObservableList из списка имен магазинов
        ObservableList<String> shopNamesObservableList = FXCollections.observableArrayList(shopNames);

        // Установка ObservableList с именами магазинов в элемент управления ListView
        ShopList.setItems(shopNamesObservableList);
    }

    public void LoadProducts() throws IOException {
        // Установка обработчика событий для ListView ShopList
        ShopList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Загрузка продуктов для выбранного магазина
                List<Product> products = db.GetProductsForShop((String) newValue); // Предположим, что у вас есть метод для получения продуктов по имени магазина
                List<String> productNames = new ArrayList<>();

                // Проход по списку продуктов и добавление их имен в список строк
                for (Product product : products) {
                    productNames.add(product.getName());
                }

                // Создание ObservableList из списка имен продуктов
                ObservableList<String> productNamesObservableList = FXCollections.observableArrayList(productNames);

                // Установка ObservableList с именами продуктов в элемент управления ListView
                ProductList.setItems(productNamesObservableList);
            }
        });
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
