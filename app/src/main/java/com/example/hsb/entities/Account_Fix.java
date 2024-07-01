package com.example.hsb.entities;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account_Fix {
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("verified")
    private boolean verified;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("created")
    private String createdDate;
    @SerializedName("updated")
    private String lastModifiedDate;
}
