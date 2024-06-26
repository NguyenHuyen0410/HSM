package com.example.hsb.service;

import com.example.hsb.entities.Role;
import com.example.hsb.response.ListResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RoleServiceApi {
    @GET("role/records")
    Call<ListResponse<Role>> getRecords(//@Header("Authorization") String token
                                            );
}
