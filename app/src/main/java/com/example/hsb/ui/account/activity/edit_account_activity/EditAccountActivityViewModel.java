package com.example.hsb.ui.account.activity.edit_account_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Account;
import com.example.hsb.repository.AccountRepository;

import java.util.List;

public class EditAccountActivityViewModel extends ViewModel {
    private MutableLiveData<Account> mAccount = new MutableLiveData<>();
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Account>> mListAccountLiveData;
    private AccountRepository accountRepository;

    public EditAccountActivityViewModel() {
        accountRepository = AccountRepository.getInstance();
        mListAccountLiveData = accountRepository.getAccountList();
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

    public MutableLiveData<List<Account>> getAccountListLiveData() {
        return mListAccountLiveData;
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