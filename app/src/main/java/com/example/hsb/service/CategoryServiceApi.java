package com.example.hsb.service;

import com.example.hsb.record.CategoryRecord;
import com.example.hsb.response.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryServiceApi {
    @GET("category/records")
    Call<CategoryResponse> getRecords();

    @POST("category/records")
    Call<CategoryResponse> createRecord(@Body CategoryRecord record);

    @PATCH("category/records/{recordId}")
    Call<CategoryResponse> updateRecord(@Path("recordId") String recordId, @Body CategoryRecord record);

    @DELETE("category/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);

}