package com.example.marketcourseprojectfx;

import com.example.marketcourseprojectfx.Controller.DbController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();

        // Создаем всплывающее окно
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup Window");

        StackPane popupRoot = new StackPane();
        Label popupLabel = new Label("App in development mode");
        popupRoot.getChildren().add(popupLabel);

        Scene popupScene = new Scene(popupRoot, 200, 100);
        popupStage.setScene(popupScene);

        // Показываем всплывающее окно
        popupStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}