package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelID, RoomDto roomDto);
    List<RoomDto> getAllRoomsInHotel(Long hotelID);
    RoomDto getRoomByID(Long roomID);
    void deleteRoomByID(Long roomID);
}
