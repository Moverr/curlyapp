package com.example.timetracker.data;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Branch {
    private int id;
    private String name;

    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Branch)) return false;
        Branch branch = (Branch) o;
        return getId() == branch.getId() && Objects.equals(getName(), branch.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }


    @NonNull
    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
