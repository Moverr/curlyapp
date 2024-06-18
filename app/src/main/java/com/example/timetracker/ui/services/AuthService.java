package com.example.timetracker.ui.services;

import com.example.timetracker.data.requests.LoginRequest;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthService {
    private static final String URL = "http://localhost:8080/hrsystem/auth/authenticate";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static AuthService instance;
    private AuthService(){

    }

    public  static  AuthService getInstance(){
        if (instance == null)
            instance = new AuthService();
        return instance;
    }

    public String validate(LoginRequest requestBody) throws IOException {

        OkHttpClient client = HttpClientSingleton.getInstance();
        Gson gson = new Gson();
        String json = gson.toJson(requestBody);

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "PHPSESSID=c2kke69srcq6j07t7ke5c8qe4m")
                .build();



        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        /*
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"email\":\"moverr@gmail.com\",\"password\":\"P@ssword?123\",\"mode\":\"token\"}");
        Request request = new Request.Builder()
                .url("http://localhost:8080/hrsystem/auth/authenticate")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "PHPSESSID=c2kke69srcq6j07t7ke5c8qe4m")
                .build();
        Response response = client.newCall(request).execute();
        */

    }
}
