package com.example.hsb.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class Category implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("category_name")
    private String name;
    @SerializedName("category_image")
    private String image;
    @SerializedName("description")
    private String description;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("created")
    private String createdDate;
    @SerializedName("updated")
    private String lastModifiedDate;
}
