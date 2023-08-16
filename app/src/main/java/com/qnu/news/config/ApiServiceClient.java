package com.qnu.news.config;

import com.qnu.news.service.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceClient {
    private static ApiService apiService;

    private ApiServiceClient() {
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            String baseUrl = "https://news-watch-production.up.railway.app/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }
}

