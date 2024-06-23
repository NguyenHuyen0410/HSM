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
public class Role {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("created")
    private LocalDateTime createdDate;
    @SerializedName("updated")
    private LocalDateTime lastModifiedDate;
}
