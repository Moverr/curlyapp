package com.example.timetracker.data.model;

import androidx.annotation.NonNull;

public class AuthEntityData {

    private String token;
    private User user;

    public String getToken() { return token; }
    public void setToken(String value) { this.token = value; }

    public User getUser() { return user; }
    public void setUser(User value) { this.user = value; }

    @NonNull
    @Override
    public String toString() {
        return "AuthEntityData{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
