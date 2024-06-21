package com.example.hsb.service;

import com.example.hsb.response.AccountResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AccountServiceApi {
    @GET("accounts/records")
    Call<AccountResponse> getRecords();

//    @POST("/api/collections/{collectionId}/records")
//    Call<Object> createRecord(@Path("collectionId") String collectionId, @Body Record record);
//
//    @PUT("/api/collections/{collectionId}/records/{recordId}")
//    Call<Object> updateRecord(@Path("collectionId") String collectionId, @Path("recordId") String recordId, @Body Record record);
//
//    @DELETE("/api/collections/{collectionId}/records/{recordId}")
//    Call<Void> deleteRecord(@Path("collectionId") String collectionId, @Path("recordId") String recordId);
}