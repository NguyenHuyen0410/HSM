package com.example.hsb.repository;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.storage.SharedPrefManager;
import com.example.hsb.utils.LoggerUtil;
import com.example.hsb.utils.RequestBodyUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private static AuthRepository instance;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "passwordConfirm";
    private static final String SUCCESS = "success";
    private static final String TOKEN = "token";
    private static final String ERROR = "error";
    public static AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    public void login(Account account) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put(USERNAME, account.getUsername());
        jsonParams.put(PASSWORD, account.getPassword());
        Call<AccountResponse> initiateLogin = RetrofitClient.getInstance().getAuthServiceApi()
                .login(RequestBodyUtil.createRequestBody(jsonParams));
        initiateLogin.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse = response.body();
                    assert accountResponse != null;
                    if (accountResponse.getAccount().isVerified()) {
                        SharedPrefManager.getInstance().put(TOKEN, accountResponse.getToken());
                        SharedPrefManager.getInstance().put("account", accountResponse.getAccount());
                    } else {
                    }
                } else {
                }
            }
            @Override
            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                LoggerUtil.e(t.getMessage());
            }
        });
    }

//    public LiveData<String> refreshToken() {
//        status = new MutableLiveData<>();
//        Call<AccountResponse> call = RetrofitClient.getInstance().getApi().refreshToken("User " + SharedPrefManager.getInstance().get(TOKEN, String.class));
//        call.enqueue(new Callback<AccountResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
//                if (response.isSuccessful()) {
//                    AccountResponse accountResponse = response.body();
//                    assert accountResponse != null;
//                    SharedPrefManager.getInstance().put(TOKEN, accountResponse.getToken());
//                    status.setValue(SUCCESS);
//                } else {
//                    status.setValue(ERROR);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
//                LoggerUtil.e(t.getMessage());
//                status.setValue(t.getMessage());
//            }
//        });
//        return status;
//    }
//
//    public LiveData<String> requestPasswordReset(String email) {
//        status = new MutableLiveData<>();
//        Map<String, Object> jsonParams = new ArrayMap<>();
//        jsonParams.put(EMAIL, email);
//        Call<Account> call = RetrofitClient.getInstance().getApi().requestForgot(RequestBodyUtil.createRequestBody(jsonParams));
//        call.enqueue(new Callback<Account>() {
//            @Override
//            public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
//                if (response.isSuccessful()) {
//                    status.setValue(SUCCESS);
//                } else {
//                    status.setValue(ERROR);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {
//                LoggerUtil.e(t.getMessage());
//                status.setValue(t.getMessage());
//            }
//        });
//        return status;
//    }
//
//    public LiveData<String> forgot(String token, String password, String confirmPassword) {
//        status = new MutableLiveData<>();
//        Map<String, Object> jsonParams = new ArrayMap<>();
//        jsonParams.put(TOKEN, token);
//        jsonParams.put(PASSWORD, password);
//        jsonParams.put(CONFIRM_PASSWORD, confirmPassword);
//        Call<AccountResponse> call = RetrofitClient.getInstance().getApi().forgot(RequestBodyUtil.createRequestBody(jsonParams));
//        call.enqueue(new Callback<AccountResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<AccountResponse> call, @NonNull Response<AccountResponse> response) {
//                if (response.isSuccessful()) {
//                    status.setValue(SUCCESS);
//                } else {
//                    status.setValue("token expired");
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
//                LoggerUtil.e(t.getMessage());
//            }
//        });
//        return status;
//    }
}
