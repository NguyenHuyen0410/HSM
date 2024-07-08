package com.example.hsb.service;

import com.example.hsb.record.PriceRecord;
import com.example.hsb.response.ListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PriceServiceApi {
    @GET("service_price/records?expand=service_id")
    Call<ListResponse<PriceRecord>> getRecords();

    @POST("service_price/records")
    Call<ListResponse<PriceRecord>> createRecord(@Body PriceRecord record);

    @PATCH("service_price/records/{recordId}")
    Call<ListResponse<PriceRecord>> updateRecord(@Path("recordId") String recordId, @Body PriceRecord record);

    @DELETE("service_price/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);
}