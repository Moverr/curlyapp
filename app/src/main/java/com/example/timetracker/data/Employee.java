package com.example.timetracker.data;

import java.util.Objects;

public class Employee {


    private long id;
    private String status;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String start_date;

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getId() == employee.getId() && Objects.equals(getStatus(), employee.getStatus()) && Objects.equals(getFirst_name(), employee.getFirst_name()) && Objects.equals(getMiddle_name(), employee.getMiddle_name()) && Objects.equals(getLast_name(), employee.getLast_name()) && Objects.equals(getStart_date(), employee.getStart_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus(), getFirst_name(), getMiddle_name(), getLast_name(), getStart_date());
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", first_name='" + first_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", start_date='" + start_date + '\'' +
                '}';
    }
}
