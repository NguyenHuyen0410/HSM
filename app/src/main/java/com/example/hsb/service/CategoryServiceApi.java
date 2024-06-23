package com.example.hsb.service;

import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.response.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryServiceApi {
    @GET("category/records")
    Call<CategoryResponse> getRecords();
//
//    @POST("accounts/records?expand=role_id")
//    Call<AccountResponse> createRecord(@Body AccountRecord record);
//
//    @PATCH("accounts/records/{recordId}?expand=role_id")
//    Call<AccountResponse> updateRecord(@Path("recordId") String recordId, @Body AccountRecord record);
//
//    @DELETE("accounts/records/{recordId}")
//    Call<Void> deleteRecord(@Path("recordId") String recordId);

}