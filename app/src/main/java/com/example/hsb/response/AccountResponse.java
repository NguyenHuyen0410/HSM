package com.example.hsb.response;

import com.example.hsb.record.AccountRecord;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse extends BaseResponse {
    @SerializedName("items")
    private List<AccountRecord> items;
}

// code cũ của chị Huyền
//private String token;
//private Account account;
//public String getToken() {
//    return token;
//}
//
//public void setToken(String token) {
//    this.token = token;
//}
//
//public Account getAccount() {
//    return account;
//}
//
//public void setAccount(Account account) {
//    this.account = account;
//}
