package com.example.timetracker.ui.services;

import com.example.timetracker.data.requests.LoginRequest;

import java.io.IOException;

public interface AuthService {


    String validate(LoginRequest requestBody) throws IOException;
}
