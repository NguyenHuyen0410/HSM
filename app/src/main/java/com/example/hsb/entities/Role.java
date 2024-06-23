package com.example.hsb.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role implements Serializable {

    public Role(String id, String name, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Role(String id, String name, boolean isDeleted, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    private String id;
    private String name;
    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
