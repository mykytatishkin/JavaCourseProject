package com.example.marketcourseprojectfx.Model;

public class Users {
    public int Id;
    public String Username;
    public String Password;
    public String Role;
    public int ShopId;

    public Users(int id,String username, String password, String role)
    {
        Id = id;
        Username = username;
        Password = password;
        Role = role;

    }

}
