package com.example.hsb.service;

import com.example.hsb.record.ServiceBillDetailRecord;
import com.example.hsb.response.ListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceBillDetailServiceApi {
    @GET("service_bill_detail/records?expand=price_id")
    Call<ListResponse<ServiceBillDetailRecord>> getRecords();

    @POST("service_bill_detail/records")
    Call<ListResponse<ServiceBillDetailRecord>> createRecord(@Body ServiceBillDetailRecord record);

    @PATCH("service_bill_detail/records/{recordId}")
    Call<ListResponse<ServiceBillDetailRecord>> updateRecord(@Path("recordId") String recordId, @Body ServiceBillDetailRecord record);

    @DELETE("service_bill_detail/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);


}