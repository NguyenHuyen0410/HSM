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
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("service_id")
    private String serviceId;
    @SerializedName("expand")
    private Expand expand;


    @Getter
    @Setter
    public static class Expand {
        @SerializedName("service_id")
        private ServiceRecord serviceRecord;
    }
}
