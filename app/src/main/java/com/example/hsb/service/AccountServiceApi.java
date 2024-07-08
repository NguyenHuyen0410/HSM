package com.example.hsb.service;

import com.example.hsb.entities.Account;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.response.ListResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountServiceApi {
//    @GET("accounts/records")
//    Call<ListResponse<Account>> getRecords(//@Header("Authorization") String token,
//                                           @Query("page") int page,
//                                           @Query("perPage") int perPage,
//                                           @Query("sort") String sort,
//                                           @Query("filter") String filter,
//                                           @Query("expand") String expand);
//
//    @POST("accounts/records")
//    Call<AccountResponse> createRecord(//@Header("Authorization") String token,
//                                       @Body RequestBody record);
//
//    @PATCH("accounts/records/{recordId}?expand=role_id")
//    Call<AccountResponse> updateRecord(//@Header("Authorization") String token,
//                                        @Path("recordId") String recordId,
//                                        @Body AccountRecord record);
    @GET("accounts/records?expand=role_id")
    Call<ListResponse<AccountRecord>> getRecords();

    @POST("accounts/records?expand=role_id")
    Call<ListResponse<AccountRecord>> createRecord(@Body AccountRecord record);

    @PATCH("accounts/records/{recordId}?expand=role_id")
    Call<ListResponse<AccountRecord>> updateRecord(@Path("recordId") String recordId, @Body AccountRecord record);

    @DELETE("accounts/records/{recordId}")
    Call<Void> deleteRecord(@Path("recordId") String recordId);

}