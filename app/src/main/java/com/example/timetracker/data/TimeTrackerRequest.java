package com.example.timetracker.data;

public class TimeTrackerRequest {
    private String time;
    private String date;
    private String type;
    private long employee;
    private String status = "approved";


    public TimeTrackerRequest() {
    }

    public TimeTrackerRequest(String time, String date, String type, long employee) {
        this.time = time;
        this.date = date;
        this.type = type;
        this.employee = employee;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getEmployee() {
        return employee;
    }

    public void setEmployee(long employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
