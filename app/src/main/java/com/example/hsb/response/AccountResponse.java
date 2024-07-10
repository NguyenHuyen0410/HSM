package com.example.hsb.response;

import com.example.hsb.record.AccountRecord;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    @SerializedName("token")
    private String token;
    @SerializedName("record")
    private AccountRecord account;
}