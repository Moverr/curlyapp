package com.example.timetracker.ui.services;

import android.util.Log;

import com.example.timetracker.data.requests.LoginRequest;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 */
public class AuthServiceImpl implements AuthService {
    private static final String URL = "http://localhost:8080/hrsystem/auth/authenticate";

    private static AuthServiceImpl instance;
    private final OkHttpClient client;
    private final Gson gson;


    private AuthServiceImpl() {
        this.client = HttpClientSingleton.getInstance();
        this.gson = new Gson();
    }

    public static synchronized AuthServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthServiceImpl();
        }
        return instance;
    }


    @Override
    public String validate(LoginRequest loginRequest) throws IOException {

        return  null;
    }


}
