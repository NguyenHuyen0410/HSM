package com.example.hsb.service;

import com.example.hsb.record.AccountRecord;
import com.example.hsb.record.ServiceCategoryRecord;
import com.example.hsb.response.ListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceCategoryServiceApi {
    @GET("service_category/records?expand=category_id")
    Call<ListResponse<ServiceCategoryRecord>> getRecords();

    @POST("service_category/records")
    Call<ServiceCategoryRecord> createRecord(@Body ServiceCategoryRecord record);

    @PATCH("service_category/records/{recordId}")
    Call<ServiceCategoryRecord> updateRecord(@Path("recordId") String recordId, @Body ServiceCategoryRecord record);

    @DELETE("service_category/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);

}