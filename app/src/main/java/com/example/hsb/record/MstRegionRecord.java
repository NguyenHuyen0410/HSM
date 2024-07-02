package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MstRegionRecord {
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
    @SerializedName("region_code")
    private String regionCode;
    @SerializedName("region_name")
    private String regionName;
}
