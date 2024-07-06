package com.example.hsb.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    private boolean isExpanded;
    private String id;
    private String name;
    private String email;
    private String password;
    private String accountStatus;
    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Role role;
    private String roleId;
    private String profileId;
    private String accountImage;

    public Account(String id, String name, String email, String password, String accountStatus,
                   boolean isDeleted, LocalDateTime createdDate,
                   LocalDateTime lastModifiedDate, Role role, String profileId, String accountImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.role = role;
        this.isExpanded = false;
        this.profileId = profileId;
        this.accountImage = accountImage;
    }

    public Account(String id, String name, String email, String password, String accountStatus,
                   boolean isDeleted, LocalDateTime createdDate,
                   LocalDateTime lastModifiedDate, String roleId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.roleId = roleId;
        this.isExpanded = false;
    }
}
