package com.example.hsb.service;

import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.ListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountServiceApi {
    @GET("accounts/records?expand=role_id")
    Call<ListResponse<AccountRecord>> getRecords();

    @POST("accounts/records?expand=role_id")
    Call<ListResponse<AccountRecord>> createRecord(@Body AccountRecord record);

    @PATCH("accounts/records/{recordId}?expand=role_id")
    Call<ListResponse<AccountRecord>> updateRecord(@Path("recordId") String recordId, @Body AccountRecord record);

    @DELETE("accounts/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);

}