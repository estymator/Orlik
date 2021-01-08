package com.example.orlik.data.model.dto;

import com.example.orlik.data.model.User;

public class UserDTO {
    private String login;
    private String name, surname;
    private int totalGames = 0;
    private int winGames = 0;
    private double trustRate=8.0;
    private String role;
    private boolean valid=true;

    public UserDTO() {
        login="";
    }

    public UserDTO(String login, String name, String surname, String role) {
        this.login=login;
        this.name=name;
        this.surname=surname;
        this.role=role;
    }




    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void incTotalGames() {
        this.totalGames++;
    }

    public int getWinGames() {
        return winGames;
    }

    public void incWinGames() {
        this.winGames++;
    }

    public double getTrustRate() {
        return trustRate;
    }

    public void setTrustRate(double trustRate) {
        this.trustRate = trustRate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public UserDTO(User u, String role){
        this.login=u.getLogin();
        this.name=u.getName();
        this.surname=u.getSurname();
        this.totalGames=u.getTotalGames();
        this.winGames=u.getWinGames();
        this.trustRate=u.getTrustRate();
        this.role=role;
        this.valid=u.isValid();
    }
}
