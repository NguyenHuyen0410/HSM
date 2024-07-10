package com.example.hsb.service;

import com.example.hsb.entities.Account;
import com.example.hsb.response.AccountResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthServiceApi {
    @POST("accounts/auth-with-password")
    Call<AccountResponse> login(@Body RequestBody params, @Query("expand") String expand);

    // refresh token api
    @POST("accounts/auth-refresh")
    Call<AccountResponse> refreshToken(@Header("Authorization") String token, @Query("expand") String expand);
}
