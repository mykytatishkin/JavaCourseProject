package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.Extension.ChangePage;
import com.example.marketcourseprojectfx.Model.Comment;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CommentController {
    public Label CommentLabel;
    public TextField CommentContent;

    private DbController db = new DbController();
    private ChangePage cp = new ChangePage();

    private Users userData;

    public void initialize() {
        try {
            loadCommentData();
            loadUserData();
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private void loadCommentData() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("Comment.txt"))) {
            String line;
            StringBuilder commentData = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                commentData.append(line);
                commentData.append("\n");
            }
            CommentLabel.setText(commentData.toString());
        }
    }

    private void loadUserData() throws IOException {
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
            System.err.println("Error reading user data: " + e.getMessage());
        }
    }

    public void PostComment(ActionEvent actionEvent) throws IOException {
        String content = CommentContent.getText().trim();
        if (!content.isEmpty()) {
            // Создаем объект комментария
            Comment comment = new Comment();
            comment.setProductId(getProductIdFromCommentFile());
            comment.setUsername(userData.getUsername()); // Используем имя пользователя из модели Users
            comment.setContent(content);

            // Сохраняем комментарий в базе данных
            db.saveComment(comment);
        } else {
            System.out.println("Comment content cannot be empty.");
        }
    }

    private int getProductIdFromCommentFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("Comment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[")) {
                    // Extract the Product ID from the line
                    int endIndex = line.indexOf("]");
                    if (endIndex != -1) {
                        return Integer.parseInt(line.substring(1, endIndex));
                    }
                }
            }
            // If Product ID is not found, throw IOException
            throw new IOException("Product ID not found in Comment.txt");
        }
    }

    public void BackToList(ActionEvent actionEvent) throws IOException {
        cp.ChangePage(actionEvent, "User");
    }
}
