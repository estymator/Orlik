package com.example.orlik.data.model;

import java.time.LocalDate;

public class Game {
    private Integer gameId;
    private Integer maxPlayersNumber,minPlayersNumber, pitchId, visibility, players;
    private String organiserLogin;
    private LocalDate gameDay;
    private String status;

    public Integer getGameId(){
        return gameId;
    }

    public Integer getMaxPlayersNumber() {
        return maxPlayersNumber;
    }

    public void setMaxPlayersNumber(Integer maxPlayersNumber) {
        this.maxPlayersNumber = maxPlayersNumber;
    }

    public Integer getMinPlayersNumber() {
        return minPlayersNumber;
    }

    public void setMinPlayersNumber(Integer minPlayersNumber) {
        this.minPlayersNumber = minPlayersNumber;
    }

    public Integer getPitchId() {
        return pitchId;
    }

    public void setPitchId(Integer pitchId) {
        this.pitchId = pitchId;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String getOrganizerLogin() {
        return organiserLogin;
    }

    public void setOrganiserLogin( String organiserLogin) {
        this.organiserLogin = organiserLogin;
    }

    public LocalDate getGameDay() {
        return gameDay;
    }

    public void setGameDay(LocalDate date) {
        this.gameDay = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPlayers() {
        return players;
    }

    public void setPlayers(Integer players) {
        this.players = players;
    }

    public Game(){};


    public Game(Integer id, Integer maxPlayersNumber, Integer minPlayersNumber, Integer pitchId, Integer visibility, String organiserLogin, LocalDate date) {
        this.gameId=id;
        this.maxPlayersNumber = maxPlayersNumber;
        this.minPlayersNumber = minPlayersNumber;
        this.pitchId = pitchId;
        this.visibility = visibility;
        this.organiserLogin = organiserLogin;
        this.gameDay = date;
    }

    @Override
    public String toString() {
        return "{" +
                "gameId=" + gameId +
                ", maxPlayersNumber=" + maxPlayersNumber +
                ", minPlayersNumber=" + minPlayersNumber +
                ", pitchId=" + pitchId +
                ", visibility=" + visibility +
                ", players=" + players +
                ", organiserLogin='" + organiserLogin + '\'' +
                ", gameDay=" + gameDay +
                ", status='" + status + '\'' +
                '}';
    }
}
