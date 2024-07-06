package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRecord {
    @SerializedName("collectionId")
    private String collectionId;
    @SerializedName("collectionName")
    private String collectionName;
    @SerializedName("created")
    private String created;
    @SerializedName("emailVisibility")
    private boolean emailVisibility;
    @SerializedName("id")
    private String id;
    @SerializedName("is_deleted")
    private boolean is_deleted;
    @SerializedName("updated")
    private String updated;
    @SerializedName("username")
    private String username;
    @SerializedName("account_password")
    private String accountPassword;
    @SerializedName("password")
    private String password;
    @SerializedName("oldPassword")
    private String oldPassword;
    @SerializedName("passwordConfirm")
    private String passwordConfirm;
    @SerializedName("account_gmail")
    private String accountGmail;
    @SerializedName("status")
    private String status;
    @SerializedName("verified")
    private boolean verified;
    @SerializedName("role_id")
    private String roleId;
    @SerializedName("profile")
    private String profileId;
    @SerializedName("expand")
    private Expand expand;

    @Getter
    @Setter
    public static class Expand {
        @SerializedName("role_id")
        private RoleRecord role;
        @SerializedName("profile")
        private AccountImage accountImage;
    }

    @Getter
    @Setter
    public static class AccountImage{
        @SerializedName("id")
        private String id;
        @SerializedName("profile_image")
        private String images;
    }
}