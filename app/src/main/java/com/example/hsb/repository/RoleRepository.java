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
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static RoleRepository getInstance() {
        if (instance == null) {
            instance = new RoleRepository();
        }
        return instance;
    }

    public LiveData<List<Role>> getRoles() {
        status = new MutableLiveData<>();
        MutableLiveData<List<Role>> roles = new MutableLiveData<>();
        Call<ListResponse<Role>> call = RetrofitClient.getInstance().getRoleServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<Role>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<Role>> call, @NonNull Response<ListResponse<Role>> response) {
                if (response.isSuccessful()) {
                    ListResponse<Role> roleResponse = response.body();
                    assert roleResponse != null;
                    roles.setValue(roleResponse.getItems());
                    status.setValue(SUCCESS);
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<Role>> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(ERROR);
            }
        });
        return roles;
    }
}
