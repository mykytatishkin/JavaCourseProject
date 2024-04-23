package com.example.marketcourseprojectfx.Controller;

import com.example.marketcourseprojectfx.Extension.ChangePage;
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

    public void initialize() {
        try {
            loadCommentData();
        } catch (IOException e) {
            System.err.println("Error loading comment data: " + e.getMessage());
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

    public void PostComment(ActionEvent actionEvent) {

    }

    public void BackToList(ActionEvent actionEvent) throws IOException {
        cp.ChangePage(actionEvent,"User");
    }
}