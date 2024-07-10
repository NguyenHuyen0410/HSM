package com.example.hsb.ui.auth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.record.AccountRecord;
import com.example.hsb.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;

    public AuthViewModel() {
        this.authRepository = AuthRepository.getInstance();
    }

    public LiveData<String> login(AccountRecord account) {
        return authRepository.login(account);
    }
}

