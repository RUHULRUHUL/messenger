package com.example.finalapp;

public class User {

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getAcountpassword() {
        return acountpassword;
    }

    public void setAcountpassword(String acountpassword) {
        this.acountpassword = acountpassword;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String userphone, String acountpassword, String imageurl, String username) {
        this.userphone = userphone;
        this.acountpassword = acountpassword;
        this.imageurl = imageurl;
        this.username = username;
    }

    private String userphone, acountpassword,imageurl,username;
}
