package com.example.orlik.data.model;

public class Friends {
    private String firstLogin, secondLogin;

    public Friends(){};


    public Friends(String firstLogin, String secondLogin) {
        this.firstLogin = firstLogin;
        this.secondLogin = secondLogin;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getSecondLogin() {
        return secondLogin;
    }

    public void setSecondLogin(String secondLogin) {
        this.secondLogin = secondLogin;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "firstLogin='" + firstLogin + '\'' +
                ", secondLogin='" + secondLogin + '\'' +
                '}';
    }
}
