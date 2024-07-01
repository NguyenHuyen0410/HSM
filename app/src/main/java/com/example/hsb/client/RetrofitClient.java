package com.example.hsb.client;

import com.example.hsb.service.AccountServiceApi;
import com.example.hsb.service.AuthServiceApi;
import com.example.hsb.service.CategoryServiceApi;
import com.example.hsb.service.RoleServiceApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static RetrofitClient instance;
    private static final String BASE_URL = "https://hotel-service-manage.pockethost.io/api/collections/";

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public AccountServiceApi getAccountServiceApi() {
        return retrofit.create(AccountServiceApi.class);
    }
    public CategoryServiceApi getCategoryServiceApi() {
        return retrofit.create(CategoryServiceApi.class);
    }
    public RoleServiceApi getRoleServiceApi() {
        return retrofit.create(RoleServiceApi.class);
    }
    public AuthServiceApi getAuthServiceApi() {
        return retrofit.create(AuthServiceApi.class);
    }
}
