package com.example.hsb.ui.account.activity.edit_account_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.R;
import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Role;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountActivityViewModel extends ViewModel {
    private MutableLiveData<Account> mAccount = new MutableLiveData<>();
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    public MutableLiveData<Account> getAccountLiveData() {
        return mAccount;
    }
    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }

    public void editAccounts(Account account) {
        AccountRecord accountRecord = new AccountRecord();
        //account field
        accountRecord.setId(account.getId());
        accountRecord.setUsername(account.getName());
        accountRecord.setAccountPassword(account.getPassword());
        accountRecord.setEmail(account.getEmail());
        accountRecord.setRoleId(account.getRole().getId());
        accountRecord.setStatus(account.getAccountStatus());

        //system field
        accountRecord.setCollectionId("scnidcpqzr1mpdh");
        accountRecord.setCollectionName("accounts");
        accountRecord.set_deleted(account.isDeleted());
        accountRecord.setEmailVisibility(true);
        accountRecord.setCreated(DateUtil.localDateTimeToString(account.getCreatedDate()));
        accountRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        accountRecord.setVerified(true);

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
                            R.drawable.android_image_1,
                            record.is_deleted(),
                            DateUtil.stringToLocalDateTime(record.getCreated()),
                            DateUtil.stringToLocalDateTime(record.getUpdated()),
                            role
                    );
                    mAccount.setValue(updatedAccount);
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
    }

    public void createAccount(Account account) {
        AccountRecord accountRecord = new AccountRecord();
        // Account fields
        accountRecord.setAccountPassword(account.getPassword());
        accountRecord.setPassword(account.getPassword());
        accountRecord.setPasswordConfirm(account.getPassword());
        accountRecord.setUsername(account.getName());
        accountRecord.setEmail(account.getEmail());
        accountRecord.setRoleId(account.getRole().getId());
        accountRecord.setStatus(account.getAccountStatus());

        // System fields
        accountRecord.setCollectionId("scnidcpqzr1mpdh");
        accountRecord.setCollectionName("accounts");
        accountRecord.setEmailVisibility(true);
        accountRecord.set_deleted(false);
        accountRecord.setVerified(false);

        System.out.println("I'm in here4");
        System.out.println("I'm in here5");
        System.out.println("I'm in here6");

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
                            R.drawable.android_image_1,
                            record.is_deleted(),
                            DateUtil.stringToLocalDateTime(record.getCreated()),
                            DateUtil.stringToLocalDateTime(record.getUpdated()),
                            role
                    );
                    mAccount.setValue(newAccount);
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
    }

    public void deleteAccount(String id){
        Call<Void> call = RetrofitClient.getInstance().getAccountServiceApi().deleteRecord(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Notify the fragment of an successful response
                    toastMessageLiveData.setValue("Response successful: " + response.message());


                } else{
                    // Notify the fragment of an unsuccessful response
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure case
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }
}