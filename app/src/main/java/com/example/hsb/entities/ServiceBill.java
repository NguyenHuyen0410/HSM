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
public class ServiceBill {
    private String id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean is_deleted;
    private boolean paymentStatus;
    private LocalDateTime billDate;
    private String roomId;
    private Room room;


}
