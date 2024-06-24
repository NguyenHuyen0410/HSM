package com.example.hsb.record;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRecord {

    @SerializedName("service_id")
    private ServiceRecordDetail serviceRecordDetail;

    @Getter
    @Setter
    public static class ServiceRecordDetail {
        @SerializedName("collectionId")
        private String collectionId;
        @SerializedName("collectionName")
        private String collectionName;
        @SerializedName("created")
        private String created;
        @SerializedName("updated")
        private String updated;
        @SerializedName("service_name")
        private String name;
        @SerializedName("service_image")
        private String image;
        @SerializedName("id")
        private String id;
        @SerializedName("start_time")
        private String startTime;
        @SerializedName("end_time")
        private String endTime;
        @SerializedName("description")
        private String description;
        @SerializedName("remark")
        private String remark;
        @SerializedName("is_deleted")
        private boolean is_deleted;
    }

}
