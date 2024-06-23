package com.example.hsb.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category implements Serializable {
    private String id;

    private String name;

    private String image;

    private String description;

    private boolean isDeleted;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
