package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.dto.HotelDto;
import com.omeshwar.project.airBNB.dto.HotelInfoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotel);
    HotelDto getHotelById(Long id);
    HotelDto updateHotelByID(Long id, HotelDto hotelDto);
    void deleteHotelByID(Long id);
    void activateHotel(Long id);
    List<HotelDto> getAllHotels();
    HotelInfoDto getHotelInfoByID(Long hotelID);
}
