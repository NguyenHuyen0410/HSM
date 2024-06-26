package com.example.hsb.repository;

import androidx.lifecycle.MutableLiveData;

public class AccountRepository {
    private static AccountRepository instance;
    private MutableLiveData<String> status;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }
}
