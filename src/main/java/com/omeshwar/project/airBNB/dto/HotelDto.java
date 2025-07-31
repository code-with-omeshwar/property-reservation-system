package com.omeshwar.project.airBNB.dto;

import com.omeshwar.project.airBNB.entity.HotelContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String city;
    private List<String> amenities;
    private List<String> photos;
    private HotelContactInfo contactInfo;
    private Boolean isActive;
}
