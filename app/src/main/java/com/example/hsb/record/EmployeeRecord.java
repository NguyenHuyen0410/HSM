package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class EmployeeRecord {
    @SerializedName("collectionId")
    private String collectionId;
    @SerializedName("collectionName")
    private String collectionName;
    @SerializedName("id")
    private String id;
    @SerializedName("created")
    private String created;
    @SerializedName("updated")
    private String updated;
    @SerializedName("is_deleted")
    private boolean is_deleted;
    @SerializedName("address")
    private String address;
    @SerializedName("dob")
    private String dob;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("gender")
    private String gender;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("remark")
    private String remark;
    @SerializedName("start_work_date")
    private String startWorkDate;
    @SerializedName("expand")
    private Expand expand;
    @SerializedName("account_id")
    private String accountId;

    @Getter
    @Setter
    public static class Expand {
        @SerializedName("account_id")
        private AccountRecord account;
        @SerializedName("nationality_id")
        private MstRegionRecord region;

        public Expand(AccountRecord accountRecord, MstRegionRecord mstRegionRecord) {
            this.account = accountRecord;
            this.region = mstRegionRecord;
        }
    }


}
