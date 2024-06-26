package com.example.hsb.ui.account.fragment.account_fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.R;
import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Role;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.repository.AccountRepository;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Account>> mListAccountLiveData;
    private AccountRepository accountRepository;
    // LiveData for toast messages
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public AccountFragmentViewModel() {
        mListAccountLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        accountRepository = AccountRepository.getInstance();
        mListAccountLiveData = accountRepository.getAccountList();
    }

    public MutableLiveData<List<Account>> getListAccountLiveData() {
        return mListAccountLiveData;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }
}
