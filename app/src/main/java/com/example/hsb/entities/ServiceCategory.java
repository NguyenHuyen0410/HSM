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
public class ServiceCategory {
    private String id;
    private String categoryId;
    private String serviceId;
    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
