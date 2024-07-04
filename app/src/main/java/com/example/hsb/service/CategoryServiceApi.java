package com.example.hsb.service;

import com.example.hsb.record.CategoryRecord;
import com.example.hsb.response.ListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryServiceApi {
    @GET("category/records?expand=service_category_via_category_id.service_id")
    Call<ListResponse<CategoryRecord>> getRecords();

    @POST("category/records")
    Call<ListResponse<CategoryRecord>> createRecord(@Body CategoryRecord record);

    @PATCH("category/records/{recordId}")
    Call<ListResponse<CategoryRecord>> updateRecord(@Path("recordId") String recordId, @Body CategoryRecord record);

    @DELETE("category/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);
}