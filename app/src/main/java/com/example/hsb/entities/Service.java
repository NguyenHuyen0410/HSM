package com.example.hsb.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Service implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("service_name")
    private String name;
    @SerializedName("service_image")
    private int image;
    @SerializedName("description")
    private String description;
    @SerializedName("start_time")
    private LocalTime startTime;
    @SerializedName("end_time")
    private LocalTime endTime;
    @SerializedName("remark")
    private String remark;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("created")
    private LocalDateTime createdDate;
    @SerializedName("updated")
    private LocalDateTime lastModifiedDate;

}
