package com.omeshwar.project.airBNB.controller;

import com.omeshwar.project.airBNB.dto.RoomDto;
import com.omeshwar.project.airBNB.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/hotels/{hotelID}/rooms")
public class RoomControllerAdmin {

    private final RoomService roomService;

    @PostMapping
    private ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelID, @RequestBody RoomDto roomDto){
        RoomDto room = roomService.createNewRoom(hotelID,roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelID){
        List<RoomDto> rooms = roomService.getAllRoomsInHotel(hotelID);
        return new ResponseEntity<>(rooms,HttpStatus.OK);
    }

    @GetMapping("/{roomID}")
    public ResponseEntity<RoomDto> getRoomByID(@PathVariable Long hotelID, @PathVariable Long roomID){
        return new ResponseEntity<>(roomService.getRoomByID(roomID),HttpStatus.OK);
    }

    @DeleteMapping("/{roomID}")
    public ResponseEntity<Void> deleteRoomByID(@PathVariable Long hotelID, @PathVariable Long roomID){
        roomService.deleteRoomByID(roomID);
        return ResponseEntity.noContent().build();
    }
}
