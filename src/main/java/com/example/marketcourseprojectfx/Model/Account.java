package com.example.marketcourseprojectfx.Model;

public class Account extends Users{
    public String FirstName;
    public String LastName;
    public String Email;
    public String Phone;

    public Account(String firstName, String lastName, String email, String phone,String username, String password, String role, int shopId) {
        super(username, password, role, shopId);
    }

    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

}
