package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCategoryRecord {
    @SerializedName("collectionId")
    private String collectionId;
    @SerializedName("collectionName")
    private String collectionName;
    @SerializedName("created")
    private String created;
    @SerializedName("id")
    private String id;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("updated")
    private String updated;
    @SerializedName("expand")
    private ServiceRecord serviceRecord;
}
