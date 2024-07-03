package com.example.timetracker.data.model;

public class AuthResponseData {
    private  AuthEntityData data;

    public AuthResponseData() {
    }

    public AuthEntityData getData() {
        return data;
    }

    public void setData(AuthEntityData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuthResponseData{" +
                "data=" + data.toString() +
                '}';
    }
}
