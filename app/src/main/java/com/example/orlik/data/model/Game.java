package com.example.orlik.data.model;

import java.time.LocalDate;

public class Game {
    private Integer gameId;
    private Integer maxPlayersNumber,minPlayersNumber, pitchId, visibility, players, duration;
    private String organizerLogin;
    private String schedule;
    private String status, description;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String getOrganizerLogin() {
        return organizerLogin;
    }

    public void setOrganizerLogin( String organiserLogin) {
        this.organizerLogin = organiserLogin;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Game(){};


    public Game(Integer id, Integer maxPlayersNumber, Integer minPlayersNumber, Integer pitchId, Integer visibility, String organiserLogin, String s, String description, Integer duration) {
        this.gameId=id;
        this.maxPlayersNumber = maxPlayersNumber;
        this.minPlayersNumber = minPlayersNumber;
        this.pitchId = pitchId;
        this.visibility = visibility;
        this.organizerLogin = organiserLogin;
        this.schedule = s;
        this.description=description;
        this.duration=duration;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", maxPlayersNumber=" + maxPlayersNumber +
                ", minPlayersNumber=" + minPlayersNumber +
                ", pitchId=" + pitchId +
                ", visibility=" + visibility +
                ", players=" + players +
                ", duration=" + duration +
                ", organizerLogin='" + organizerLogin + '\'' +
                ", schedule='" + schedule + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
