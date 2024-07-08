package com.example.hsb.response;

import com.example.hsb.entities.Account;
import com.example.hsb.entities.Account_Fix;
import com.example.hsb.record.AccountRecord;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse{
    @SerializedName("token")
    private String token;
    @SerializedName("record")
    private Account_Fix account;
}