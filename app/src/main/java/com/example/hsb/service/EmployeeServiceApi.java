package com.example.hsb.service;

import com.example.hsb.record.EmployeeRecord;
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

public interface EmployeeServiceApi {
    @GET("employee/records")
    Call<ListResponse<EmployeeRecord>> getRecords(
            @Query("expand") String expand,
            @Query("filter") String filter
    );

    @POST("employee/records/?expand=account_id,nationality_id")
    Call<EmployeeRecord> createRecord(@Body EmployeeRecord record);

    @PATCH("employee/records/{recordId}?expand=account_id,nationality_id")
    Call<EmployeeRecord> updateRecord(@Path("recordId") String recordId, @Body EmployeeRecord record);

    @Multipart
    @PATCH("employee/records/{recordId}")
    Call<EmployeeRecord> uploadFile(
            @Path("recordId") String recordId,
            @Part MultipartBody.Part profileImage
    );

    @DELETE("employee/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);
}
