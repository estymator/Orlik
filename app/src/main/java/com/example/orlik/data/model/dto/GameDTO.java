package com.example.orlik.data.model.dto;

public class GameDTO {
    private int id, pitchId, maxPlayersNumber, minPlayersNumber, visibility, players, pitchRating, duration;
    private String description, organizerLogin, schedule, status, location, address, pitchType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPitchId() {
        return pitchId;
    }

    public void setPitchId(int pitchId) {
        this.pitchId = pitchId;
    }

    public int getMaxPlayersNumber() {
        return maxPlayersNumber;
    }

    public void setMaxPlayersNumber(int maxPlayersNumber) {
        this.maxPlayersNumber = maxPlayersNumber;
    }

    public int getMinPlayersNumber() {
        return minPlayersNumber;
    }

    public void setMinPlayersNumber(int minPlayersNumber) {
        this.minPlayersNumber = minPlayersNumber;
    }

    public String getPitchType() {
        return pitchType;
    }

    public void setPitchType(String pitchType) {
        this.pitchType = pitchType;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getPitchRating() {
        return pitchRating;
    }

    public void setPitchRating(int pitchRating) {
        this.pitchRating = pitchRating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizerLogin() {
        return organizerLogin;
    }

    public void setOrganizerLogin(String organizerLogin) {
        this.organizerLogin = organizerLogin;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GameDTO(int id, int pitchId, int maxPlayersNumber, int minPlayersNumber, int visibility, int players, int pitchRating,
                   String description, String organizerLogin, String schedule, String status, String location, String address, String pitchType, Integer duration) {
        this.id = id;
        this.pitchId = pitchId;
        this.maxPlayersNumber = maxPlayersNumber;
        this.minPlayersNumber = minPlayersNumber;
        this.visibility = visibility;
        this.players = players;
        this.pitchRating = pitchRating;
        this.description = description;
        this.organizerLogin = organizerLogin;
        this.schedule = schedule;
        this.status = status;
        this.location = location;
        this.address = address;
        this.pitchType = pitchType;
        this.duration=duration;
    }
}
