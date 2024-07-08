package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRecord {
    @SerializedName("collectionId")
    private String collectionId;
    @SerializedName("collectionName")
    private String collectionName;
    @SerializedName("created")
    private String created;
    @SerializedName("updated")
    private String updated;
    @SerializedName("start_date")
    private String startTime;
    @SerializedName("end_date")
    private String endTime;
    @SerializedName("remark")
    private String remark;
    @SerializedName("id")
    private String id;
    @SerializedName("service_id")
    private String serviceId;
    @SerializedName("service_price")
    private String servicePrice;
    @SerializedName("is_deleted")
    private boolean isDeleted;

}
