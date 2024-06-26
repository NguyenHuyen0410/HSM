package com.example.hsb.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.hsb.R;
import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Role;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.storage.AccountConstant;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {
    private static AccountRepository instance;
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Account>> getAccountList() {
        MutableLiveData<List<Account>> mListAccountLiveData = new MutableLiveData<>();
        List<Account> accountList = new ArrayList<>();
        Call<AccountResponse> call = RetrofitClient.getInstance().getAccountServiceApi().getRecords();
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AccountRecord> records = response.body().getItems();
                    for (AccountRecord record : records) {
                        Role role = new Role(record.getExpand().getRole().getId(), record.getExpand().getRole().getName(), record.getExpand().getRole().isDeleted(),
                                DateUtil.stringToLocalDateTime(record.getExpand().getRole().getCreated()), DateUtil.stringToLocalDateTime(record.getExpand().getRole().getUpdated()));
                        Account account = new Account(record.getId(), record.getUsername(), record.getEmail(),
                                record.getAccountPassword(), record.getStatus(), R.drawable.hotel_logo, record.is_deleted(),
                                DateUtil.stringToLocalDateTime(record.getCreated()), DateUtil.stringToLocalDateTime(record.getUpdated()),
                                role);
                        accountList.add(account);
                    }
                    mListAccountLiveData.setValue(accountList);
                } else {
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
        return mListAccountLiveData;
    }

    public interface EditAccountCallback {
        void onEditSuccess(Account updatedAccount);
        void onEditFailure(String errorMessage);
    }

    public interface CreateAccountCallback {
        void onCreateSuccess(Account newAccount);
        void onCreateFailure(String errorMessage);
    }

    public interface DeleteAccountCallback {
        void onDeleteSuccess();
        void onDeleteFailure(String errorMessage);
    }

    public void editAccount(Account account, EditAccountCallback callback) {
        AccountRecord accountRecord = setAccountRecord(account);
        Call<AccountResponse> call = RetrofitClient.getInstance().getAccountServiceApi().updateRecord(account.getId(), accountRecord);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountResponse accountResponse = response.body();
                    AccountRecord record = accountResponse.getItems().get(0);
                    Role role = new Role(record.getExpand().getRole().getId(), record.getExpand().getRole().getName(), record.getExpand().getRole().isDeleted(),
                            DateUtil.stringToLocalDateTime(record.getExpand().getRole().getCreated()), DateUtil.stringToLocalDateTime(record.getExpand().getRole().getUpdated()));
                    Account updatedAccount = new Account(record.getId(), record.getUsername(), record.getEmail(), record.getPassword(), record.getStatus(),
                            R.drawable.hotel_logo, record.is_deleted(), DateUtil.stringToLocalDateTime(record.getCreated()), DateUtil.stringToLocalDateTime(record.getUpdated()), role);
                    callback.onEditSuccess(updatedAccount);
                } else {
                    callback.onEditFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                callback.onEditFailure(t.getMessage());
            }
        });
    }

    public void createAccount(Account account, CreateAccountCallback callback) {
        AccountRecord accountRecord = setAccountRecord(account);
        Call<AccountResponse> call = RetrofitClient.getInstance().getAccountServiceApi().createRecord(accountRecord);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountResponse accountResponse = response.body();
                    AccountRecord record = accountResponse.getItems().get(0);
                    Role role = new Role(record.getExpand().getRole().getId(), record.getExpand().getRole().getName(), record.getExpand().getRole().isDeleted(),
                            DateUtil.stringToLocalDateTime(record.getExpand().getRole().getCreated()), DateUtil.stringToLocalDateTime(record.getExpand().getRole().getUpdated()));
                    Account newAccount = new Account(record.getId(), record.getUsername(), record.getEmail(), record.getPassword(), record.getStatus(),
                            R.drawable.hotel_logo, record.is_deleted(), DateUtil.stringToLocalDateTime(record.getCreated()), DateUtil.stringToLocalDateTime(record.getUpdated()), role);
                    callback.onCreateSuccess(newAccount);
                } else {
                    callback.onCreateFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                callback.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deleteAccount(String accountId, DeleteAccountCallback callback) {
        Call<Void> call = RetrofitClient.getInstance().getAccountServiceApi().deleteRecord(accountId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onDeleteSuccess();
                } else {
                    callback.onDeleteFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onDeleteFailure(t.getMessage());
            }
        });
    }

    private AccountRecord setAccountRecord(Account account) {
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setVerified(false);
        if (account.getId() != null) {
            accountRecord.setId(account.getId());
            accountRecord.setCreated(DateUtil.localDateTimeToString(account.getCreatedDate()));
            accountRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            accountRecord.setVerified(true);
        } else {
            accountRecord.setPassword(account.getPassword());
            accountRecord.setPasswordConfirm(account.getPassword());
            accountRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            accountRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        accountRecord.setAccountPassword(account.getPassword());
        accountRecord.setUsername(account.getName());
        accountRecord.setEmail(account.getEmail());
        accountRecord.setStatus(account.getAccountStatus());
        accountRecord.setRoleId(account.getRole().getId());
        accountRecord.set_deleted(account.isDeleted());
        return accountRecord;
    }
}