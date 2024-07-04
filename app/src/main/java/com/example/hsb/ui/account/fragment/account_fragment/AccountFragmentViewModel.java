package com.example.hsb.ui.account.fragment.account_fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Account;
import com.example.hsb.repository.AccountRepository;

import java.util.List;

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
