package com.example.fragmenttest.Model;

public class User {
    private String  uid;
    private String username;
    private String firstname;
    private String lastname;
    private String mail;
    private String birthday;
    private String photo;

    public User()
    {
     super();
    }

    public String getBirthday() {
        return birthday;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMail() {
        return mail;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
