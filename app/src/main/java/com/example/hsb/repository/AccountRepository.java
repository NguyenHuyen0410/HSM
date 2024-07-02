package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Role;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {
    private static AccountRepository instance;
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Account>> mListAccountLiveData = new MutableLiveData<>();

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Account>> getAccountList() {
        fetchAccountList();
        return mListAccountLiveData;
    }

    private void fetchAccountList() {
        List<Account> accountList = new ArrayList<>();
        Call<ListResponse<AccountRecord>> call = RetrofitClient.getInstance().getAccountServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<AccountRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<AccountRecord>> call, @NonNull Response<ListResponse<AccountRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AccountRecord> records = response.body().getItems();
                    for (AccountRecord record : records) {
                        accountList.add(setAccount(record));
                    }
                    mListAccountLiveData.setValue(accountList);
                } else {
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<AccountRecord>> call, @NonNull Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
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
        Call<AccountRecord> call = RetrofitClient.getInstance().getAccountServiceApi().updateRecord(account.getId(), accountRecord);
        call.enqueue(new Callback<AccountRecord>() {
            @Override
            public void onResponse(@NonNull Call<AccountRecord> call, @NonNull Response<AccountRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountRecord record = response.body();
                    callback.onEditSuccess(setAccount(record));
                } else {
                    callback.onEditFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<AccountRecord> call, @NonNull Throwable t) {
                callback.onEditFailure(t.getMessage());
            }
        });
    }

    public void createAccount(Account account, CreateAccountCallback callback) {
        AccountRecord accountRecord = setAccountRecord(account);
        Call<AccountRecord> call = RetrofitClient.getInstance().getAccountServiceApi().createRecord(accountRecord);
        call.enqueue(new Callback<AccountRecord>() {
            @Override
            public void onResponse(@NonNull Call<AccountRecord> call, @NonNull Response<AccountRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountRecord record = response.body();
                    callback.onCreateSuccess(setAccount(record));
                } else {
                    callback.onCreateFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<AccountRecord> call, @NonNull Throwable t) {
                callback.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deleteAccount(String accountId, DeleteAccountCallback callback) {
        Call<Void> call = RetrofitClient.getInstance().getAccountServiceApi().deleteRecord(accountId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchAccountList();  // Fetch updated account list after deletion
                    callback.onDeleteSuccess();
                } else {
                    callback.onDeleteFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onDeleteFailure(t.getMessage());
            }
        });
    }

    private Account setAccount(AccountRecord record){
        Role role = new Role(record.getExpand().getRole().getId(), record.getExpand().getRole().getName(), record.getExpand().getRole().isDeleted(),
                DateUtil.apiDateTimeStringToLocalDateTime(record.getExpand().getRole().getCreated()), DateUtil.apiDateTimeStringToLocalDateTime(record.getExpand().getRole().getUpdated()));
        return new Account(record.getId(), record.getUsername(), record.getAccountGmail(), record.getAccountPassword(), record.getStatus(),
                record.is_deleted(), DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()), DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated()),
                role, record.getExpand().getAccountImage().getId(), record.getExpand().getAccountImage().getImages());
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
            accountRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            accountRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        accountRecord.setAccountPassword(account.getPassword());
        accountRecord.setPassword(account.getPassword());
        accountRecord.setPasswordConfirm(account.getPassword());
        accountRecord.setUsername(account.getName());
        accountRecord.setAccountGmail(account.getEmail());
        accountRecord.setStatus(account.getAccountStatus());
        accountRecord.setRoleId(account.getRole().getId());
        String randomId = UUID.randomUUID().toString();
        accountRecord.setProfileId(randomId);
        accountRecord.set_deleted(account.isDeleted());
        return accountRecord;
    }
}