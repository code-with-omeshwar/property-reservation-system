package com.omeshwar.project.airBNB.controller;

import com.omeshwar.project.airBNB.dto.HotelDto;
import com.omeshwar.project.airBNB.dto.HotelInfoDto;
import com.omeshwar.project.airBNB.dto.HotelPriceDto;
import com.omeshwar.project.airBNB.dto.HotelSearchRequest;
import com.omeshwar.project.airBNB.service.HotelService;
import com.omeshwar.project.airBNB.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelPriceDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelID}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfoByID(@PathVariable Long hotelID){
        return ResponseEntity.ok(hotelService.getHotelInfoByID(hotelID));
    }

}
