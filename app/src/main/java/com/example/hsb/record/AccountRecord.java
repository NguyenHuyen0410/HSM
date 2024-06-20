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
    @SerializedName("verified")
    private boolean verified;


}
