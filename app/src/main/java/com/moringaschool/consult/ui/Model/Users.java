package com.moringaschool.consult.ui.Model;

public class Users {


    String username, imageURL, id, status, email, profession, phone, password;

    public Users(String username, String imageURL, String id, String status, String email, String profession, String phone, String password) {
        this.username = username;
        this.imageURL = imageURL;
        this.id = id;
        this.status = status;
        this.email = email;
        this.profession = profession;
        this.phone = phone;
        this.password = password;
    }

    public Users() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
