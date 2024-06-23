package com.example.hsb.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Service {
    private String id;

    private String name;

    private String image;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String remark;

    private LocalDateTime createdDate;

    private boolean isDeleted;

    private LocalDateTime lastModifiedDate;
}
