package com.example.orlik.data.model;

import com.example.orlik.data.model.dto.UserDTO;

import java.io.Serializable;

public class User implements Serializable {
    private int totalGames=0, winGames=0;
    private double trustRate=0;
    private String login="", name="", surname="";
    private String fetchError="";
    private boolean valid=true;

    public User(int totalGames, int winGames, String login, String name,  String surname, double trustRate, boolean valid) {

        this.totalGames = totalGames;
        this.winGames = winGames;
        this.login = login;
        this.name=name;
        this.surname = surname;
        this.trustRate=trustRate;
        this.valid=valid;
    }

    public User() {
    }

    public User(String error){
        this.fetchError=error;
    }
    public User(UserDTO userDTO){
        this.totalGames = userDTO.getTotalGames();
        this.winGames = userDTO.getWinGames();
        this.login = userDTO.getLogin();
        this.name=userDTO.getName();
        this.surname = userDTO.getSurname();
        this.trustRate=userDTO.getTrustRate();
        this.valid=userDTO.isValid();
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

    public double getTrustRate() {
        return trustRate;
    }

    public void setTrustRate(double trustRate) {
        this.trustRate = trustRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFetchError() {
        return fetchError;
    }

    public void setFetchError(String fetchError) {
        this.fetchError = fetchError;
    }

    public boolean getValid() {
        return valid;
    }
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "{" +
                "totalGames=" + totalGames +
                ", winGames=" + winGames +
                ", trustRate=" + trustRate +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", fetchError='" + fetchError + '\'' +
                ", valid=" + valid +
                "}";
    }
}
