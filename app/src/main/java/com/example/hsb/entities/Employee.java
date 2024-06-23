package com.example.hsb.entities;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @SerializedName("id")
    private String id;
    @SerializedName("account_id")
    private int accountId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("dob")
    private LocalDateTime dob;
    @SerializedName("gender")
    private String gender;
    @SerializedName("nationality_id")
    private int nationalityId;
    @SerializedName("address")
    private String address;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("start_work_date")
    private LocalDateTime startWorkDate;
    @SerializedName("remark")
    private String remark;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("created")
    private LocalDateTime createdDate;
    @SerializedName("updated")
    private LocalDateTime lastModifiedDate;
}
