package com.example.marketcourseprojectfx.Controller;
import com.example.marketcourseprojectfx.Extension.ChangePage;
import com.example.marketcourseprojectfx.Model.Users;
import javafx.event.ActionEvent;

import java.io.IOException;

public class UserController {

    // Data Binding
    private Users userData;
    private ChangePage cp = new ChangePage();

    public void setUserData(Users user) {
        if (user != null) {
            this.userData = user;
        }
        else if (user == null) {
            System.err.println("User data is null.");
        }
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        cp.ChangePage(actionEvent,"SignUp");
    }


}
