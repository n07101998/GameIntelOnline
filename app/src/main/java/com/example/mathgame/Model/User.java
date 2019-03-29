package com.example.mathfastgame.Model;

public class User {
    int id;
    int point;
    String nameUser;

    public User(int id, int point, String nameUser) {
        this.id = id;
        this.point = point;
        this.nameUser = nameUser;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
