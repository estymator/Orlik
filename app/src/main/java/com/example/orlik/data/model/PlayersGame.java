package com.example.orlik.data.model;

public class PlayersGame {
    private Integer gameId;
    private String playerId;
    private int team;

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public PlayersGame() {
    }

    public PlayersGame(Integer gameId, String playerId, int team) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.team = team;
    }
}
