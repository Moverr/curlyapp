package com.example.timetracker.data.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class AuthEntity {
    private AuthEntityData data;
    private boolean authResponsePublic;

    public AuthEntity() {
    }

    public AuthEntityData getData() {
        return data;
    }

    public void setData(AuthEntityData data) {
        this.data = data;
    }

    public boolean isAuthResponsePublic() {
        return authResponsePublic;
    }

    public void setAuthResponsePublic(boolean authResponsePublic) {
        this.authResponsePublic = authResponsePublic;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthEntity)) return false;
        AuthEntity that = (AuthEntity) o;
        return isAuthResponsePublic() == that.isAuthResponsePublic() && Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData(), isAuthResponsePublic());
    }


    @NonNull
    @Override
    public String toString() {
        return "AuthEntity{" +
                "data=" + data +
                ", authResponsePublic=" + authResponsePublic +
                '}';
    }
}
