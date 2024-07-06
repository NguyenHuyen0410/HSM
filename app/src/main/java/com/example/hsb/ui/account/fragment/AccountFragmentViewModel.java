package com.example.hsb.ui.account.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.hsb.entities.Account;
import com.example.hsb.repository.AccountRepository;
import java.util.List;
import lombok.Getter;

public class AccountFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Account>> mListAccountLiveData;

    @Getter
    private MutableLiveData<String> toastMessageLiveData;

    public AccountFragmentViewModel() {
        mListAccountLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        AccountRepository accountRepository = AccountRepository.getInstance();
        mListAccountLiveData = accountRepository.getAccountList();
    }

    public MutableLiveData<List<Account>> getListAccountLiveData() {
        return mListAccountLiveData;
    }

}
