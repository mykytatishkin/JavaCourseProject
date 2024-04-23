package com.example.marketcourseprojectfx.Controller;
import com.example.marketcourseprojectfx.Extension.ChangePage;
import com.example.marketcourseprojectfx.Model.Product;
import com.example.marketcourseprojectfx.Model.Shop;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class UserController {
    public ListView ShopList;
    public ListView ProductList;
    public ListView CartList;
    private Users userData;
    private String username;
    private DbController db = new DbController();
    private ChangePage cp = new ChangePage();

    public void initialize() throws IOException {
        setUserDataFromTextFile();
        LoadShops();
        LoadProducts();
        LoadCartItems();
        if (userData != null) {
            username = userData.getUsername();
        } else {
            System.err.println("User data is null.");
        }
    }

    public void LoadCartItems() {
        ObservableList<String> cartItems = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader("cart.txt"))) {
            // Пропускаем первую строку
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                cartItems.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading cart file: " + e.getMessage());
        }
        CartList.setItems(cartItems);
    }

    public void LoadShops() throws IOException {
        List<Shop> shops = db.GetAllShops();
        List<String> shopNames = new ArrayList<>();
        for (Shop shop : shops) {
            shopNames.add(shop.getName());
        }
        ShopList.setItems(FXCollections.observableArrayList(shopNames));
    }

    public void LoadProducts() throws IOException {
        ShopList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                List<Product> products = db.GetProductsForShop((String)newValue);
                ProductList.setItems(FXCollections.observableArrayList(products.stream().map(Product::getName).toList()));
            }
        });
    }

    public void setUserDataFromTextFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            userData = new Users();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    switch (parts[0].trim()) {
                        case "Username":
                            userData.setUsername(parts[1].trim());
                            break;
                        case "Role":
                            userData.setRole(parts[1].trim());
                            break;
                        case "ShopId":
                            userData.setShopId(Integer.parseInt(parts[1].trim()));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        cp.ChangePage(actionEvent, "LogIn");
    }

    public void BuyItem(ActionEvent actionEvent) {
        String selectedProduct = (String) ProductList.getSelectionModel().getSelectedItem();
        Product product = db.GetProductByName(selectedProduct);

        if (product != null && product.getQuantity() > 0) {
            db.UpdateProductQuantity(product.getId(), product.getQuantity() - 1);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("cart.txt", true))) {
                if (userData != null) {
                    username = userData.getUsername();
                    if (!isUserAlreadyInCart()) {
                        writer.write(username);
                        writer.newLine();
                    }
                    writer.write(product.getName());
                    writer.newLine();
                } else {
                    System.err.println("User data is null. Unable to proceed with the purchase.");
                }
                // Обновление отображения после каждого купленного предмета
                LoadCartItems();
            } catch (IOException e) {
                System.err.println("Error writing to cart file: " + e.getMessage());
            }
        } else {
            System.out.println("Product is not available or out of stock.");
        }
    }

    private boolean isUserAlreadyInCart() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(userData.getUsername())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading cart file: " + e.getMessage());
        }
        return false;
    }

    private boolean isUserMatches(String cartUserLine) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String userLine = reader.readLine();
            return getUsernameFromLine(userLine).equals(getUsernameFromLine(cartUserLine));
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
            return false;
        }
    }

    private String getUsernameFromLine(String line) {
        if (line != null && line.startsWith("Username:")) {
            return line.split(":")[1].trim();
        }
        return null;
    }

    private boolean isCartEmpty() {
        File cartFile = new File("cart.txt");
        return cartFile.exists() && cartFile.length() == 0;
    }

    private String getFirstLineOfFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    private void clearCart() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("cart.txt"))) {
            writer.print("");
        } catch (IOException e) {
            System.err.println("Error clearing cart file: " + e.getMessage());
        }
    }

    public void RateItem(ActionEvent actionEvent) throws IOException {
        // Получаем id продукта из ProductList
        int productId = getProductIdFromProductList();
        // Увеличиваем рейтинг продукта на 1 в базе данных
        db.IncreaseProductRating(productId);
        // Обновляем отображение списка продуктов
        LoadProducts();
    }

    // Метод для получения id продукта из ProductList
    private int getProductIdFromProductList() {
        // Получаем выбранный продукт из списка
        String selectedProduct = (String) ProductList.getSelectionModel().getSelectedItem();
        // Получаем объект продукта по его имени
        Product product = db.GetProductByName(selectedProduct);
        // Возвращаем id продукта
        return product.getId();
    }

    public void CommentItem(ActionEvent actionEvent) throws IOException {
        String selectedProduct = (String) ProductList.getSelectionModel().getSelectedItem();
        Product product = db.GetProductByName(selectedProduct);
        writeCommentToFile(product.getId(), product.getName());
        cp.ChangePage(actionEvent, "Comment");
    }

    private void writeCommentToFile(int productId, String productName) {
        clearCommentFile(); // Очистка файла перед записью новых данных
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Comment.txt"))) {
            writer.write("[" + productId + "] " + productName); // Запись в формате [ID] Name
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to Comment.txt file: " + e.getMessage());
        }
    }

    private void clearCommentFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Comment.txt"))) {
            writer.print(""); // Перезапись файла пустой строкой
        } catch (IOException e) {
            System.err.println("Error clearing Comment.txt file: " + e.getMessage());
        }
    }

    public void OrderShopsById(ActionEvent actionEvent) {
        List<Shop> shops = db.GetAllShopsOrderedById();
        List<String> shopNames = new ArrayList<>();
        for (Shop shop : shops) {
            shopNames.add(shop.getName());
        }
        ShopList.setItems(FXCollections.observableArrayList(shopNames));
    }

    public void OrderShopsByAddress(ActionEvent actionEvent) {
        List<Shop> shops = db.GetAllShopsOrderedByAddress();
        List<String> shopNames = new ArrayList<>();
        for (Shop shop : shops) {
            shopNames.add(shop.getName());
        }
        ShopList.setItems(FXCollections.observableArrayList(shopNames));
    }

    public void OrderShopsByEmail(ActionEvent actionEvent) {
        List<Shop> shops = db.GetAllShopsOrderedByEmail();
        List<String> shopNames = new ArrayList<>();
        for (Shop shop : shops) {
            shopNames.add(shop.getName());
        }
        ShopList.setItems(FXCollections.observableArrayList(shopNames));
    }


    public void CancelFromCart(ActionEvent actionEvent) {
        // Получаем выбранный элемент из CartList
        String selectedItem = (String) CartList.getSelectionModel().getSelectedItem();

        // Проверяем, выбран ли элемент
        if (selectedItem != null) {
            // Получаем список элементов в CartList
            ObservableList<String> cartItems = CartList.getItems();

            // Находим последнее вхождение выбранного элемента в списке
            int lastIndex = cartItems.lastIndexOf(selectedItem);

            // Проверяем, найдено ли последнее вхождение
            if (lastIndex != -1) {
                // Удаляем элемент по индексу
                cartItems.remove(lastIndex);

                // Перезаписываем файл cart.txt с обновленным списком элементов
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("cart.txt"))) {
                    for (String item : cartItems) {
                        writer.write(item);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.err.println("Error writing to cart file: " + e.getMessage());
                }

                // Обновляем отображение списка элементов в CartList
                CartList.setItems(cartItems);
            } else {
                System.out.println("Item not found in cart.");
            }
        } else {
            System.out.println("No item selected.");
        }
    }

    public void BuyFromCart(ActionEvent actionEvent) {
        // Получаем выбранный элемент из CartList
        String selectedItem = (String) CartList.getSelectionModel().getSelectedItem();

        // Проверяем, выбран ли элемент
        if (selectedItem != null) {
            // Создаем диалоговое окно подтверждения
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation of buying");
            alert.setHeaderText("Are you sure you want to buy \"" + selectedItem + "\"?");
            alert.setContentText("After confirmation, you won`t be able to cancel that action.");

            // Отображаем диалоговое окно и ждем реакции пользователя
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Удаляем выбранный элемент из списка
                    CartList.getItems().remove(selectedItem);
                }
            });
        } else {
            System.out.println("No item selected.");
        }
    }
}
