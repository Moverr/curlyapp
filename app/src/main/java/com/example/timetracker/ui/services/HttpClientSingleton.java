package com.example.timetracker.ui.services;

import okhttp3.OkHttpClient;

public class HttpClientSingleton {
    private static OkHttpClient instance;

    private HttpClientSingleton() {}

    public static OkHttpClient getInstance() {
        if (instance == null) {
            instance = new OkHttpClient.Builder()
                    .build();
        }
        return instance;
    }


}
