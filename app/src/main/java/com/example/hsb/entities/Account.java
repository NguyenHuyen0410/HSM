package com.example.hsb.entities;

import com.example.hsb.response.ExpandResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account implements Serializable {
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
    @SerializedName("role_id")
    private int roleId;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("created")
    private String createdDate;
    @SerializedName("updated")
    private String lastModifiedDate;
    @SerializedName("expand")
    private ExpandResponse expand;
}
