package com.example.hsb.service;

import com.example.hsb.record.ServiceBillRecord;
import com.example.hsb.response.ListResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceBillServiceApi {
    @GET("service_bill/records")
    Call<ListResponse<ServiceBillRecord>> getRecords(
            @Query("expand") String expand,
            @Query("filter") String filter
    );

    @POST("service_bill/records?expand=room_id")
    Call<ServiceBillRecord> createRecord(@Body ServiceBillRecord record);

    @PATCH("service_bill/records/{recordId}?expand=room_id")
    Call<ServiceBillRecord> updateRecord(@Path("recordId") String recordId, @Body ServiceBillRecord record);

    @DELETE("service_bill/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);
}
