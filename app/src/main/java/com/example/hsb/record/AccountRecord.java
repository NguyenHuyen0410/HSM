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
    @SerializedName("passwordConfirm")
    private String passwordConfirm;
    @SerializedName("email")
    private String email;
    @SerializedName("status")
    private String status;
    @SerializedName("verified")
    private boolean verified;
    @SerializedName("role_id")
    private String roleId;
    @SerializedName("expand")
    private Expand expand;

    @Getter
    @Setter
    public static class Expand {
        @SerializedName("role_id")
        private RoleRecord role;
    }
}