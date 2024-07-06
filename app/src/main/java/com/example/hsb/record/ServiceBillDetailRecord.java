package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceBillDetailRecord {
    @SerializedName("collectionId")
    private String collectionId;
    @SerializedName("collectionName")
    private String collectionName;
    @SerializedName("created")
    private String created;
    @SerializedName("id")
    private String id;
    @SerializedName("service_id")
    private String serviceId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("service_status")
    private String status;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("updated")
    private String updated;
    @SerializedName("bill_id")
    private String billId;
    @SerializedName("price_id")
    private String priceId;
    @SerializedName("remark")
    private String remark;

    @SerializedName("expand")
    private Expand expand;

    @Getter
    @Setter
    public static class Expand {
        @SerializedName("price_id")
        private PriceRecord priceRecord;
    }

}
