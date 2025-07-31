package com.omeshwar.project.airBNB.controller;

import com.omeshwar.project.airBNB.dto.HotelDto;
import com.omeshwar.project.airBNB.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/hotels")
public class HotelControllerAdmin {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        log.info("Attempting to creating new hotel with name: {}",hotelDto.getName());
        return new ResponseEntity<>(hotelService.createNewHotel(hotelDto), HttpStatus.CREATED);
    }

    @GetMapping("/{hotelID}")
    public ResponseEntity<HotelDto> getHotelByID(@PathVariable Long hotelID){
        log.info("Attempting to find hotel by id: {}",hotelID);
        return ResponseEntity.ok(hotelService.getHotelById(hotelID));
    }

    @PutMapping("/{hotelID}")
    public ResponseEntity<HotelDto> updateHotelByID(@PathVariable Long hotelID, @RequestBody HotelDto hotelDto){
        log.info("Attempting to update hotel by id: {}",hotelID);
        return ResponseEntity.ok(hotelService.updateHotelByID(hotelID,hotelDto));
    }

    @DeleteMapping("/{hotelID}")
    public ResponseEntity<Void> deleteHotelByID(@PathVariable Long hotelID){
        hotelService.deleteHotelByID(hotelID);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelID}")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelID){
        hotelService.activateHotel(hotelID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels(){
        return new ResponseEntity<>(hotelService.getAllHotels(),HttpStatus.FOUND);
    }

}
