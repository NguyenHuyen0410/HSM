package com.example.hsb.service;

import com.example.hsb.entities.Role;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.response.ListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RoleService {
    @GET("role/records")
    Call<ListResponse<Role>> getRoleList(@Query("page") int page,
                                         @Query("perPage") int perPage,
                                         @Query("sort") String sort,
                                         @Query("filter") String filter,
                                         @Query("expand") String expand);
}
