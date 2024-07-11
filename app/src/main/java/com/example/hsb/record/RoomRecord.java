package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoomRecord {
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
    @SerializedName("room_number")
    private String roomNumber;
    @SerializedName("room_type")
    private String roomType;
    @SerializedName("capacity")
    private int roomCapacity;
    @SerializedName("room_area")
    private int roomArea;
    @SerializedName("room_image")
    private String roomImage;
    @SerializedName("description")
    private String description;
    @SerializedName("device_account_id")
    private String deviceAccountId;
    @SerializedName("status")
    private String status;
    @SerializedName("remark")
    private String remark;
    @SerializedName("expand")
    private Expand expand;

    @Setter
    @Getter
    public static class Expand{
        @SerializedName("device_account_id")
        private AccountRecord account;
        public Expand(AccountRecord account){
            this.account = account;
        }
    }
}
