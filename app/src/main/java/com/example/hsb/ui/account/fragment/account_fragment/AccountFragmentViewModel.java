package com.example.hsb.ui.account.fragment.account_fragment;

import android.os.Build;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.R;
import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.AccountRole;
import com.example.hsb.entities.Role;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragmentViewModel extends ViewModel {

    private List<Account> accountList;
    private List<AccountRole> accountRoleList = new ArrayList<>();
    private MutableLiveData<List<Account>> mListAccountLiveData;
    private MutableLiveData<List<AccountRole>> mListAccountRoleLiveData;

    // LiveData for toast messages
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public AccountFragmentViewModel(){
        mListAccountLiveData = new MutableLiveData<>();
        mListAccountRoleLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        accountList = new ArrayList<>();
        callApi();
        mListAccountLiveData.setValue(accountList);
        mListAccountRoleLiveData.setValue(accountRoleList);
    }

    public MutableLiveData<List<Account>> getListAccountLiveData(){
        return mListAccountLiveData;
    }

    public MutableLiveData<List<AccountRole>> getListAccountRoleLiveData(){
        return mListAccountRoleLiveData;
    }

    private void callApi() {
        Call<AccountResponse> call = RetrofitClient.getInstance().getAccountServiceApi().getRecords();
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AccountRecord> records = response.body().getItems();
                    int index = 0;
                    for (AccountRecord record : records) {
                        index++;
                        // Process each record
                        Account account = new Account(record.getId(), record.getUsername(), "john.doe@example.com",
                                "password9", "active",
                                R.drawable.android_image_1, record.is_deleted(), DateUtil.stringToLocalDateTime(record.getCreated()), DateUtil.stringToLocalDateTime(record.getUpdated()));
                        accountList.add(account);
                        accountRoleList.add(new AccountRole("accountRole" + index, account.getId(), "role1", false, LocalDateTime.now(), LocalDateTime.now()));
                        System.out.println("Added account: " + account.toString());
                    }

                    // Moved the System.out.println statements inside the callback
                    if (!accountList.isEmpty()) {
                        System.out.println("First account: " + accountList.get(0).toString());
                        if (accountList.size() > 1) {
                            System.out.println("Second account: " + accountList.get(1).toString());
                            if (accountList.size() > 2) {
                                System.out.println("Third account: " + accountList.get(2).toString());
                            }
                        }
                    }

                    // Update LiveData after data is added
                    mListAccountLiveData.setValue(accountList);
                    mListAccountRoleLiveData.setValue(accountRoleList);
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
}
