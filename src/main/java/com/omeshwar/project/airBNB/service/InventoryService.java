package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.dto.HotelDto;
import com.omeshwar.project.airBNB.dto.HotelPriceDto;
import com.omeshwar.project.airBNB.dto.HotelSearchRequest;
import com.omeshwar.project.airBNB.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);
    void deleteInventories(Room room);
    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
