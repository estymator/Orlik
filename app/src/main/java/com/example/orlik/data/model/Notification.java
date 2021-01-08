package com.example.orlik.data.model;

public class Notification {
    private Integer id;
    private String destinationLogin, sourceLogin;
    private int type;   //1-friends 2-game

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestinationLogin() {
        return destinationLogin;
    }

    public void setDestinationLogin(String destinationLogin) {
        this.destinationLogin = destinationLogin;
    }

    public String getSourceLogin() {
        return sourceLogin;
    }

    public void setSourceLogin(String sourceLogin) {
        this.sourceLogin = sourceLogin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Notification() {
    }

    public Notification(Integer id, String destinationLogin, String sourceLogin, int type) {
        this.id = id;
        this.destinationLogin = destinationLogin;
        this.sourceLogin = sourceLogin;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", destinationLogin='" + destinationLogin + '\'' +
                ", sourceLogin='" + sourceLogin + '\'' +
                ", type=" + type +
                '}';
    }
}
