package com.omeshwar.project.airBNB.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelSearchRequest {
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomsCount;
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
}
