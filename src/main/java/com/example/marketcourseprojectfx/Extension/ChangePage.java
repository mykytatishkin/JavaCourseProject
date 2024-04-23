package com.example.marketcourseprojectfx.Extension;

import com.example.marketcourseprojectfx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePage {
    public void ChangePage(ActionEvent actionEvent, String page) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page + ".fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

}
