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
    private static MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Account>> getAccountList(){
        MutableLiveData<List<Account>> mListAccountLiveData = new MutableLiveData<>();
        List<Account> accountList = new ArrayList<>();
        Call<AccountResponse> call = RetrofitClient.getInstance().getAccountServiceApi().getRecords();
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AccountRecord> records = response.body().getItems();
                    int index = 0;
                    for (AccountRecord record : records) {
                        index++;
                        Role role = new Role(record.getExpand().getRole().getId(), record.getExpand().getRole().getName(), record.getExpand().getRole().isDeleted(),
                                DateUtil.stringToLocalDateTime(record.getExpand().getRole().getCreated()), DateUtil.stringToLocalDateTime(record.getExpand().getRole().getUpdated()));
                        // Process each record
                        Account account = new Account(record.getId(), record.getUsername(), record.getEmail(),
                                record.getAccountPassword(), record.getStatus(), R.drawable.hotel_logo, record.is_deleted(),
                                DateUtil.stringToLocalDateTime(record.getCreated()), DateUtil.stringToLocalDateTime(record.getUpdated()),
                                role);
                        accountList.add(account);
                    }
                    mListAccountLiveData.setValue(accountList);
                } else {
                    // Notify the fragment of an unsuccessful response
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                // Handle the failure case
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
        return mListAccountLiveData;
    }

    public MutableLiveData<Account> editAccounts(Account account) {
        MutableLiveData<Account> mAccount = new MutableLiveData<>();
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
                    Account updatedAccount = new Account(
                            record.getId(),
                            record.getUsername(),
                            account.getEmail(),
                            account.getPassword(),
                            account.getAccountStatus(),
                            R.drawable.hotel_logo,
                            record.is_deleted(),
                            DateUtil.stringToLocalDateTime(record.getCreated()),
                            DateUtil.stringToLocalDateTime(record.getUpdated()),
                            role
                    );
                    mAccount.setValue(updatedAccount);
                    toastMessageLiveData.setValue("Update account successful: " + response.message());
                } else {
                    // Notify the fragment of an unsuccessful response
                    toastMessageLiveData.setValue("Update account not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                // Handle the failure case
                toastMessageLiveData.setValue("Update account failed: " + t.getMessage());
            }
        });
        return mAccount;
    }

    public void createAccount(Account account) {
        MutableLiveData<Account> mAccount = new MutableLiveData<>();
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
                    Account newAccount = new Account(
                            record.getId(),
                            record.getUsername(),
                            account.getEmail(),
                            account.getPassword(),
                            account.getAccountStatus(),
                            R.drawable.hotel_logo,
                            record.is_deleted(),
                            DateUtil.stringToLocalDateTime(record.getCreated()),
                            DateUtil.stringToLocalDateTime(record.getUpdated()),
                            role
                    );
                    mAccount.setValue(newAccount);
                    toastMessageLiveData.setValue("Create account successful: " + response.message());
                } else {
                    // Notify the fragment of an unsuccessful response
                    toastMessageLiveData.setValue("Create account not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                // Handle the failure case
                toastMessageLiveData.setValue("Create account failed: " + t.getMessage());
            }
        });
    }

    public void deleteAccount(String id) {
        MutableLiveData<Account> mAccount = new MutableLiveData<>();
        Call<Void> call = RetrofitClient.getInstance().getAccountServiceApi().deleteRecord(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Notify the fragment of a successful response
                    toastMessageLiveData.setValue("Delete successful: " + response.message());
                    // Set the account LiveData to null to indicate deletion
                    mAccount.setValue(null);
                } else {
                    // Log the response code and message
                    int responseCode = response.code();
                    String responseMessage = response.message();
                    // Notify the fragment of an unsuccessful response
                    toastMessageLiveData.setValue("Delete not successful: " + responseMessage + " (Code: " + responseCode + ")");
                    Log.e("DeleteAccount", "Unsuccessful response: Code " + responseCode + ", Message: " + responseMessage);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure case
                toastMessageLiveData.setValue("Delete failed: " + t.getMessage());
                Log.e("DeleteAccount", "Request failed", t);
            }
        });
    }

    public AccountRecord setAccountRecord(Account account){
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setVerified(false);
        if(account.getId()!=null){
            accountRecord.setId(account.getId());
            accountRecord.setCreated(DateUtil.localDateTimeToString(account.getCreatedDate()));
            accountRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            accountRecord.setVerified(true);
        }
        else{
            accountRecord.setPassword(account.getPassword());
            accountRecord.setPasswordConfirm(account.getPassword());
            accountRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            accountRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        // Account fields
        accountRecord.setAccountPassword(account.getPassword());
        accountRecord.setUsername(account.getName());
        accountRecord.setEmail(account.getEmail());
        accountRecord.setRoleId(account.getRole().getId());
        accountRecord.setStatus(account.getAccountStatus());

        // System fields
        accountRecord.setCollectionId("scnidcpqzr1mpdh");
        accountRecord.setCollectionName(AccountConstant.ACCOUNT);
        accountRecord.setEmailVisibility(true);
        accountRecord.set_deleted(false);
        return accountRecord;
    }

}
