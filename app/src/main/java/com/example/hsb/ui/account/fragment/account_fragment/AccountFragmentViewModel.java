package com.example.hsb.ui.account.fragment.account_fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.R;
import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Role;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragmentViewModel extends ViewModel {
    private List<Account> accountList;
    private MutableLiveData<List<Account>> mListAccountLiveData;

    // LiveData for toast messages
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public AccountFragmentViewModel() {
        mListAccountLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        accountList = new ArrayList<>();
        callApi();
        mListAccountLiveData.setValue(accountList);
    }

    public MutableLiveData<List<Account>> getListAccountLiveData() {
        return mListAccountLiveData;
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
                        Role role = new Role(record.getExpand().getRole().getId(), record.getExpand().getRole().getName(), record.getExpand().getRole().isDeleted(),
                                DateUtil.stringToLocalDateTime(record.getExpand().getRole().getCreated()), DateUtil.stringToLocalDateTime(record.getExpand().getRole().getUpdated()));
                        // Process each record
                        Account account = new Account(record.getId(), record.getUsername(), record.getEmail(),
                                record.getAccountPassword(), record.getStatus(), R.drawable.android_image_1, record.is_deleted(),
                                DateUtil.stringToLocalDateTime(record.getCreated()), DateUtil.stringToLocalDateTime(record.getUpdated()),
                                role);
                        accountList.add(account);
                    }

                    // Update LiveData after data is added
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
    }
}
