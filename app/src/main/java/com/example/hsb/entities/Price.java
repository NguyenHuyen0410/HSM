package com.example.hsb.entities;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Price {
    private String id;

    private String serviceId;

    private Double price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String remark;

    private boolean isDeleted;

    private LocalDateTime created;

    private LocalDateTime updated;

}