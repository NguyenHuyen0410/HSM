package com.example.hsb.repository;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.record.RoomRecord;
import com.example.hsb.record.ServiceBillRecord;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.response.ListResponse;
import com.example.hsb.storage.SharedPrefManager;
import com.example.hsb.utils.LoggerUtil;
import com.example.hsb.utils.RequestBodyUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private static AuthRepository instance;
    private MutableLiveData<String> status;
    private static final String IDENTITY = "identity";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "passwordConfirm";
    private static final String TOKEN = "token";
    private static final String ERROR = "FAILED";
    private static final String EMAIL = "email";
    private static final String TERMINATE = "TERMINATE";
    private static final String IS_DELETED = "ISDELETED";

    public static AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }
    public LiveData<String> login(AccountRecord account) {
        status = new MutableLiveData<>();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put(IDENTITY, account.getUsername());
        jsonParams.put(PASSWORD, account.getPassword());
        Call<AccountResponse> call = RetrofitClient.getInstance().getAuthServiceApi().login(RequestBodyUtil.createRequestBody(jsonParams), "role_id");
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountResponse accountResponse = response.body();
                    String accountStatus = accountResponse.getAccount().getStatus();
                    if (TERMINATE.equalsIgnoreCase(accountStatus)) {
                        status.setValue(TERMINATE);
                    } else if (IS_DELETED.equalsIgnoreCase(accountStatus)) {
                        status.setValue(IS_DELETED);
                    } else {
                        SharedPrefManager.getInstance().put(TOKEN, accountResponse.getToken());
                        SharedPrefManager.getInstance().put("account", accountResponse.getAccount());
                        String roleName = accountResponse.getAccount().getExpand().getRole().getName();
                        status.setValue(roleName.toUpperCase());
                    }
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(t.getMessage());
            }
        });
        return status;
    }

    public LiveData<String> refreshToken() {
        status = new MutableLiveData<>();
        Call<AccountResponse> call = RetrofitClient.getInstance().getAuthServiceApi().refreshToken(SharedPrefManager.getInstance().get(TOKEN, String.class), "role_id");
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountResponse accountResponse = response.body();
                    String accountStatus = accountResponse.getAccount().getStatus();
                    if (TERMINATE.equalsIgnoreCase(accountStatus)) {
                        status.setValue(TERMINATE);
                    } else if (IS_DELETED.equalsIgnoreCase(accountStatus)) {
                        status.setValue(IS_DELETED);
                    } else {
                        SharedPrefManager.getInstance().put(TOKEN, accountResponse.getToken());
                        String roleName = accountResponse.getAccount().getExpand().getRole().getName();
                        status.setValue(roleName.toUpperCase());
                    }
                } else {
                    status.setValue(ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
                status.setValue(t.getMessage());
            }
        });
        return status;
    }
}
