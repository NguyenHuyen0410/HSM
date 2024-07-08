package com.example.hsb.service;

import com.example.hsb.entities.Account;
import com.example.hsb.response.AccountResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthServiceApi {
    @POST("accounts/auth-with-password")
    Call<AccountResponse> login(@Body RequestBody params);
    // refresh token api
    @POST("accounts/auth-refresh")
    Call<AccountResponse> refreshToken(@Header("Authorization") String token);

    // user request forgot password api
    @POST("accounts/request-password-reset")
    Call<Account> requestPasswordReset(@Body RequestBody params);

    // user forgot password api
    @POST("users/confirm-password-reset")
    Call<AccountResponse> confirmPasswordReset(@Body RequestBody params);
}
