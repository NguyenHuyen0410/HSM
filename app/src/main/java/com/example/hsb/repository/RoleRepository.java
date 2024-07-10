package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Role;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.LoggerUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoleRepository {
    private static RoleRepository instance;
    private final MutableLiveData<List<Role>> rolesLiveData = new MutableLiveData<>();

    public static RoleRepository getInstance() {
        if (instance == null) {
            instance = new RoleRepository();
        }
        return instance;
    }

    public LiveData<List<Role>> getRolesLiveData() {
        if (rolesLiveData.getValue() == null) {
            fetchRoles();
        }
        return rolesLiveData;
    }

    private void fetchRoles() {
        Call<ListResponse<Role>> call = RetrofitClient.getInstance().getRoleServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<Role>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Role>> call, @NonNull Response<ListResponse<Role>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rolesLiveData.postValue(response.body().getItems());
                } else {
                    rolesLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<Role>> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                rolesLiveData.postValue(null);
            }
        });
    }

}
