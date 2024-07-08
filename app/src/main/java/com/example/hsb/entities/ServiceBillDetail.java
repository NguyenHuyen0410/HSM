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
public class ServiceBillDetail {
    private String id;

    private String serviceId;

    private int quantity;

    private String status;

    private String remark;

    private String billId;

    private String priceId;

    private boolean isDeleted;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Price price;
}