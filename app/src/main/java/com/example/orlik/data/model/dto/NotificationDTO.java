package com.example.orlik.data.model.dto;

import androidx.annotation.Nullable;

import com.example.orlik.data.model.Notification;
import com.example.orlik.data.model.User;

public class NotificationDTO {
    private Integer id;
    private String destinationLogin, sourceLogin;
    private int type;   //1-friends 2-game
    @Nullable
    private GameDTO game;
    @Nullable
    private User user;

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

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NotificationDTO() {
    }

    public NotificationDTO(Notification notification, GameDTO game) {
        this.id=notification.getId();
        this.sourceLogin=notification.getSourceLogin();
        this.destinationLogin=notification.getDestinationLogin();
        this.type=notification.getType();
        this.game = game;
    }

    public NotificationDTO(Notification notification, User user) {
        this.id=notification.getId();
        this.sourceLogin=notification.getSourceLogin();
        this.destinationLogin=notification.getDestinationLogin();
        this.type=notification.getType();
        this.user = user;
    }
}
