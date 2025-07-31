package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.dto.RoomDto;
import com.omeshwar.project.airBNB.entity.Hotel;
import com.omeshwar.project.airBNB.entity.Room;
import com.omeshwar.project.airBNB.exception.ResourceNotFoundException;
import com.omeshwar.project.airBNB.repository.HotelRepository;
import com.omeshwar.project.airBNB.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImp implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelID, RoomDto roomDto) {
        log.info("Creating room in hotel with ID: {}",hotelID);
        Hotel hotel = hotelRepository
                .findById(hotelID)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel does not exists by ID: "+hotelID));
        Room room = modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if (hotel.getIsActive()){
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelID) {
        log.info("Get all rooms in a hotel with hotel ID: {}",hotelID);
        Hotel hotel = hotelRepository
                .findById(hotelID)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel does not exists by ID: "+hotelID));
        return hotel.getRooms()
                .stream()
                .map(room -> modelMapper.map(room,RoomDto.class))
                .toList();
    }

    @Override
    public RoomDto getRoomByID(Long roomID) {
        log.info("Getting Room by ID: {}",roomID);
        Room room = roomRepository
                .findById(roomID)
                .orElseThrow(()->new ResourceNotFoundException("Room does not exists by ID: " + roomID));
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public void deleteRoomByID(Long roomID) {
        log.info("Delete room with id: {}",roomID);
        Room room = roomRepository
                .findById(roomID)
                .orElseThrow(()->new ResourceNotFoundException("Room does not exists by ID: " + roomID));
        inventoryService.deleteFutureInventory(room);
        roomRepository.deleteById(roomID);
    }
}
