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
    private MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private AccountRepository accountRepository;

    public EditAccountActivityViewModel() {
        accountRepository = AccountRepository.getInstance();
    }

    public MutableLiveData<Account> getAccountLiveData() {
        return mAccount;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }

    public MutableLiveData<Boolean> getDeleteStatusLiveData() {
        return deleteStatusLiveData;
    }

    public void editAccount(Account account) {
        accountRepository.editAccount(account, new AccountRepository.EditAccountCallback() {
            @Override
            public void onEditSuccess(Account updatedAccount) {
                mAccount.postValue(updatedAccount);
                toastMessageLiveData.postValue("Account updated successfully.");
            }

            @Override
            public void onEditFailure(String errorMessage) {
                toastMessageLiveData.postValue("Update account failed: " + errorMessage);
            }
        });
    }

    public void createAccount(Account account) {
        accountRepository.createAccount(account, new AccountRepository.CreateAccountCallback() {
            @Override
            public void onCreateSuccess(Account newAccount) {
                mAccount.postValue(newAccount);
                toastMessageLiveData.postValue("Account created successfully.");
            }

            @Override
            public void onCreateFailure(String errorMessage) {
                toastMessageLiveData.postValue("Create account failed: " + errorMessage);
            }
        });
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteAccount(accountId, new AccountRepository.DeleteAccountCallback() {
            @Override
            public void onDeleteSuccess() {
                deleteStatusLiveData.postValue(true);
            }

            @Override
            public void onDeleteFailure(String errorMessage) {
                toastMessageLiveData.postValue("Delete account failed: " + errorMessage);
            }
        });
    }
}