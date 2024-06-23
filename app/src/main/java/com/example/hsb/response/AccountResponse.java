package com.example.hsb.response;

import com.example.hsb.entities.Account;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountResponse {
    @SerializedName("token")
    private String token;
    @SerializedName("record")
    private Account account;
}
