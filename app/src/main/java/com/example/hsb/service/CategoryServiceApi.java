package com.example.hsb.service;

import com.example.hsb.entities.Category;
import com.example.hsb.record.CategoryRecord;
import com.example.hsb.response.ListResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoryServiceApi {
    @GET("category/records")
    Call<ListResponse<Category>> getRecords(//@Header("Authorization") String token,
//                                            @Query("page") int page,
//                                            @Query("perPage") int perPage,
//                                            @Query("sort") String sort,
//                                            @Query("filter") String filter,
//                                            @Query("expand") String expand
    );

    @POST("category/records")
    Call<Category> createRecord(//@Header("Authorization") String token,
                                @Body RequestBody record);

    @PATCH("category/records/{recordId}")
    Call<Category> updateRecord(//@Header("Authorization") String token,
                                @Path("recordId") String recordId,
                                @Body RequestBody record);
//
//    @DELETE("category/records/{recordId}")
//    Call<Void> deleteRecord(@Path("recordId") String recordId);

}