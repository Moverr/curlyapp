package com.example.timetracker.data.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class User {

    private String id;
    private String status;
    private String role;
    private String first_name;
    private String last_name;
    private String email;
    private String timezone;
    private String locale;
    private String localeOptions;
    private String avatar;
    private String company;
    private String title;
    private String externalID;
    private String theme;
    private String the2FaSecret;
    private String passwordResetToken;


    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocaleOptions() {
        return localeOptions;
    }

    public void setLocaleOptions(String localeOptions) {
        this.localeOptions = localeOptions;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExternalID() {
        return externalID;
    }

    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getThe2FaSecret() {
        return the2FaSecret;
    }

    public void setThe2FaSecret(String the2FaSecret) {
        this.the2FaSecret = the2FaSecret;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getStatus(), user.getStatus()) && Objects.equals(getRole(), user.getRole()) && Objects.equals(getFirst_name(), user.getFirst_name()) && Objects.equals(getLast_name(), user.getLast_name()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getTimezone(), user.getTimezone()) && Objects.equals(getLocale(), user.getLocale()) && Objects.equals(getLocaleOptions(), user.getLocaleOptions()) && Objects.equals(getAvatar(), user.getAvatar()) && Objects.equals(getCompany(), user.getCompany()) && Objects.equals(getTitle(), user.getTitle()) && Objects.equals(getExternalID(), user.getExternalID()) && Objects.equals(getTheme(), user.getTheme()) && Objects.equals(getThe2FaSecret(), user.getThe2FaSecret()) && Objects.equals(getPasswordResetToken(), user.getPasswordResetToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus(), getRole(), getFirst_name(), getLast_name(), getEmail(), getTimezone(), getLocale(), getLocaleOptions(), getAvatar(), getCompany(), getTitle(), getExternalID(), getTheme(), getThe2FaSecret(), getPasswordResetToken());
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", timezone='" + timezone + '\'' +
                ", locale='" + locale + '\'' +
                ", localeOptions='" + localeOptions + '\'' +
                ", avatar='" + avatar + '\'' +
                ", company='" + company + '\'' +
                ", title='" + title + '\'' +
                ", externalID='" + externalID + '\'' +
                ", theme='" + theme + '\'' +
                ", the2FaSecret='" + the2FaSecret + '\'' +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                '}';
    }
}
