package com.omeshwar.project.airBNB.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long hotelID;
    private Long roomID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer roomsCount;
}
