package com.example.timetracker.ui.services;

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
    private static final MediaType mediaType = MediaType.get("application/json; charset=utf-8");

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
    public String validate(LoginRequest requestBody) throws IOException {


        String json = gson.toJson(requestBody);
        RequestBody body = RequestBody.create(json, mediaType);

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
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
