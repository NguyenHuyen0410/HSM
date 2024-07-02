package com.example.hsb.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee implements Serializable {
    private boolean isExpanded;

    private String id;
    private String address;
    private LocalDateTime dob;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String profileImage;
    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String remark;
    private LocalDateTime startWorkDate;
    private Account account;
    private String accountId;
    private MstRegion region;

    public Employee(String id, String address, LocalDateTime dob, String firstName, String lastName,
                    String gender, String phoneNumber, String profileImage, boolean isDeleted,
                    LocalDateTime createdDate, LocalDateTime lastModifiedDate, String remark, LocalDateTime startWorkDate,
                    Account account, MstRegion region) {
        this.id = id;
        this.address = address;
        this.dob = dob;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.remark = remark;
        this.startWorkDate = startWorkDate;
        this.account = account;
        this.region = region;
    }
}
