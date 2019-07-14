package com.example.demoretrofit2.model.signup;

public class SignUpModel {
    public String email;
    public String password;
    public String imgUser;

    public SignUpModel(String email, String password, String imgUser) {
        this.email = email;
        this.password = password;
        this.imgUser = imgUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }
}
