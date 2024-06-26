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
    private boolean isExpanded; //MVVM

    public Account(String id, String name, String email, String password, String accountStatus,
                   int images, boolean isDeleted, LocalDateTime createdDate,
                   LocalDateTime lastModifiedDate, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.images = images;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.role = role;
        this.isExpanded = false;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    private String id;
    private String name;
    private String email;
    private String password;
    private String accountStatus;
    private int images;
    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Role role;
}
