package com.example.hsb.ui.account.activity.edit_account_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Account;
import com.example.hsb.entities.Employee;
import com.example.hsb.repository.AccountRepository;
import com.example.hsb.repository.EmployeeRepository;

import lombok.Getter;

public class EditAccountActivityViewModel extends ViewModel {
    private final MutableLiveData<Account> mAccount = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;

    public EditAccountActivityViewModel() {
        accountRepository = AccountRepository.getInstance();
        employeeRepository = EmployeeRepository.getInstance();
    }

    public MutableLiveData<Account> getAccountLiveData() {
        return mAccount;
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
        Employee employee = new Employee();
        employee.setAccountId(account.getId());
        employeeRepository.createEmployee(employee, new EmployeeRepository.CreateEmployeeCallBack() {
            @Override
            public void onCreateSuccess(Employee employee) {
                account.setProfileId(employee.getId());
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
            @Override
            public void onCreateFailure(String errorMessage) {
                toastMessageLiveData.postValue("Create profile failed: " + errorMessage);
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