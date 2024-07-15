package com.example.hsb.service;

import com.example.hsb.record.ServiceRecord;
import com.example.hsb.response.ListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServicesServiceApi {
    @GET("services/records/expand=service_category_via_service_id.category_id")
    Call<ListResponse<ServiceRecord>> getRecords();

    @POST("services/records")
    Call<ListResponse<ServiceRecord>> createRecord(@Body ServiceRecord record);

    @PATCH("services/records/{recordId}")
    Call<ListResponse<ServiceRecord>> updateRecord(@Path("recordId") String recordId, @Body ServiceRecord record);

    @DELETE("services/records{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);
}