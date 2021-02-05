package com.example.orlik.data.model;

import java.io.Serializable;

public class PlayerStatistics implements Serializable {
    private String playerId;
    private Integer gameId;
    private Integer rate, minutesPlayed, goals, assists, cleanSheets;
    private Integer fairplayRate;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(Integer minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(Integer cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    public Integer getFairplayRate() {
        return fairplayRate;
    }

    public void setFairplayRate(Integer fairplayRate) {
        this.fairplayRate = fairplayRate;
    }

    public PlayerStatistics(){};

    public PlayerStatistics(String playerId, Integer gameId, Integer rate, Integer minutesPlayed, Integer goals, Integer assists, Integer cleanSheets, Integer fairplayRate) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.rate = rate;
        this.minutesPlayed = minutesPlayed;
        this.goals = goals;
        this.assists = assists;
        this.cleanSheets = cleanSheets;
        this.fairplayRate = fairplayRate;
    }
}
