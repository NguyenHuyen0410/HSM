package com.example.hsb.domain;

import com.example.hsb.entities.Account;

public class AccountResponse {
    private String token;
    private Account account;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
