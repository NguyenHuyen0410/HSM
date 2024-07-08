package com.example.hsb.service;

import com.example.hsb.record.RoomRecord;
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

public interface RoomServiceApi {
    @GET("rooms/records")
    Call<RoomRecord> getRecord(
            @Query("expand") String expand,
            @Query("filter") String filter
    );

    @GET("rooms/records?expand=device_account_id")
    Call<ListResponse<RoomRecord>> getRecords();

    @POST("rooms/records?expand=device_account_id")
    Call<RoomRecord> createRecord(@Body RoomRecord record);

    @PATCH("rooms/records/{recordId}?expand=device_account_id")
    Call<RoomRecord> updateRecord(@Path("recordId") String recordId, @Body RoomRecord record);

    @Multipart
    @PATCH("rooms/records/{recordId}")
    Call<RoomRecord> uploadFile(
            @Path("recordId") String recordId,
            @Part MultipartBody.Part profileImage
    );

    @DELETE("rooms/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);
}
