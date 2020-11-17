package com.example.orlik.data.model;

public class User {
    private int totalGames=0, winGames=0, trustRate=0;
    private String login="", name="", surname="";
    private String fetchError="";

    public User(int totalGames, int winGames, String login, String name,  String surname, int trustRate) {

        this.totalGames = totalGames;
        this.winGames = winGames;
        this.login = login;
        this.name=name;
        this.surname = surname;
        this.trustRate=trustRate;
    }

    public User(String error){
        this.fetchError=error;
    }


    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getWinGames() {
        return winGames;
    }

    public void setWinGames(int winGames) {
        this.winGames = winGames;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTrustRate() {
        return trustRate;
    }

    public void setTrustRate(int trustRate) {
        this.trustRate = trustRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
