package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceBillRecord {
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
    @SerializedName("payment_status")
    private boolean paymentStatus;
    @SerializedName("bill_date")
    private String billDate;
    @SerializedName("room_id")
    private String roomId;
    @SerializedName("expand")
    private Expand expand;

    @Setter
    @Getter
    public static class Expand{
        @SerializedName("room_id")
        private RoomRecord room;

        public Expand(RoomRecord room){
            this.room = room;
        }
    }
}
