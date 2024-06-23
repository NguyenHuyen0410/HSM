package com.example.hsb.service;

import com.example.hsb.entities.Account;
import com.example.hsb.response.AccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import okhttp3.RequestBody;

public interface AuthService {
    //login api
    @POST("accounts/auth-with-password")
    Call<AccountResponse> login(@Body RequestBody params);
    // refresh token api
    @POST("accounts/auth-refresh")
    Call<AccountResponse> refreshToken(@Header("Authorization") String token);
    // request password reset api
    @POST("accounts/request-password-reset")
    Call<Account> requestForgot(@Body RequestBody params);
    // confirm password reset api
    @POST("accounts/confirm-password-reset")
    Call<AccountResponse> forgot(@Body RequestBody params);
}
