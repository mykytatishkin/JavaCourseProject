package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.Extension.ChangePage;
import com.example.marketcourseprojectfx.Model.Comment;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CommentController {
    public Label CommentLabel;
    public TextField CommentContent;
    public ListView<String> CommentList; // Обратите внимание, что ListView теперь имеет тип String

    private DbController db = new DbController();
    private ChangePage cp = new ChangePage();

    private Users userData;

    public void initialize() {
        try {
            loadCommentData();
            loadUserData();
        } catch (IOException | SQLException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private void loadCommentData() throws IOException, SQLException {
        // Получаем комментарии из базы данных по текущему productId
        List<Comment> comments = db.getCommentsByProductId(getProductIdFromCommentFile());
        ObservableList<String> commentStrings = FXCollections.observableArrayList();

        for (Comment comment : comments) {
            // Форматируем каждый комментарий в соответствии с требуемым форматом [Username] Content
            String formattedComment = "[" + comment.getUsername() + "] " + comment.getContent();
            commentStrings.add(formattedComment);
        }

        CommentList.setItems(commentStrings); // Устанавливаем отформатированные комментарии в ListView
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

    public void PostComment(ActionEvent actionEvent) throws IOException, SQLException {
        String content = CommentContent.getText().trim();
        if (!content.isEmpty()) {
            // Создаем объект комментария
            Comment comment = new Comment();
            comment.setProductId(getProductIdFromCommentFile());
            comment.setUsername(userData.getUsername()); // Используем имя пользователя из модели Users
            comment.setContent(content);

            // Сохраняем комментарий в базе данных
            db.saveComment(comment);

            // После сохранения комментария загружаем комментарии для текущего productId
            loadCommentData();
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
