package com.example.hsb.entities;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @SerializedName("id")
    private String id;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("category_image")
    private String categoryImage;
    @SerializedName("description")
    private String description;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("created")
    private LocalDateTime createdDate;
    @SerializedName("updated")
    private LocalDateTime lastModifiedDate;
}
