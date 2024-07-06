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
public class Room {
    private String id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean is_deleted;
    private String roomNumber;
    private String roomType;
    private int roomCapacity;
    private int roomArea;
    private String roomImage;
    private String description;
    private String deviceAccountId;
    private String status;
    private String remark;
    private Account account;

    public Room(String id, LocalDateTime created, LocalDateTime updated, boolean is_deleted, String roomNumber, String roomType,
                int roomCapacity, int roomArea, String roomImage, String description, String deviceAccountId, String status, String remark) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.is_deleted = is_deleted;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomCapacity = roomCapacity;
        this.roomArea = roomArea;
        this.roomImage = roomImage;
        this.description = description;
        this.deviceAccountId = deviceAccountId;
        this.status = status;
        this.remark = remark;
    }
}
