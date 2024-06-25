package com.example.hsb.ui.account.activity.edit_account_activity;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.R;
import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Role;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.repository.AccountRepository;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.storage.AccountConstant;
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
    private AccountRepository accountRepository;

    public void editAccounts(Account account) {
        accountRepository = AccountRepository.getInstance();
        accountRepository.editAccounts(account);
    }

    public void createAccount(Account account) {
        accountRepository = AccountRepository.getInstance();
        accountRepository.createAccount(account);
    }

    public void deleteAccount(String id) {
        accountRepository = AccountRepository.getInstance();
        accountRepository.deleteAccount(id);
    }
}