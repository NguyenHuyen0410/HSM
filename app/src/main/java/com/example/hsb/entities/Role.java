package com.example.hsb.entities;

import com.example.hsb.utils.DateUtil;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("created")
    private String createdDate;
    @SerializedName("updated")
    private String lastModifiedDate;


    public Role(String id, String name, boolean isDeleted, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
        this.createdDate = DateUtil.localDateTimeToString(createdDate);
        this.lastModifiedDate = DateUtil.localDateTimeToString(lastModifiedDate);
    }

    public Role(String id, String name, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
    }
}
