package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRecord {
    @SerializedName("collectionId")
    private String collectionId;
    @SerializedName("collectionName")
    private String collectionName;
    @SerializedName("created")
    private String created;
    @SerializedName("updated")
    private String updated;
    @SerializedName("category_name")
    private String name;
    @SerializedName("category_image")
    private String image;
    @SerializedName("id")
    private String id;
    @SerializedName("description")
    private String description;
    @SerializedName("is_deleted")
    private boolean isDeleted;

    @SerializedName("expand")
    private Expand expand;

    @Getter
    @Setter
    public static class Expand {
        @SerializedName("service_category_via_category_id")
        private List<ServiceCategoryRecord> serviceCategoryRecordList;
    }

}
