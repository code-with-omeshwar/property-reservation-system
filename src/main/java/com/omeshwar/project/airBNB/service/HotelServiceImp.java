package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.dto.HotelDto;
import com.omeshwar.project.airBNB.entity.Hotel;
import com.omeshwar.project.airBNB.entity.Room;
import com.omeshwar.project.airBNB.exception.ResourceNotFoundException;
import com.omeshwar.project.airBNB.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImp implements HotelService{

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new Hotel with name: {}",hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setIsActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}",hotel.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel Details not found with ID: "+id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelByID(Long id, HotelDto hotelDto) {
        log.info("Updating hotel by id: {}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel Details not found with ID: "+id));
        return modelMapper.map(hotelRepository.save(modelMapper.map(hotelDto,Hotel.class)),HotelDto.class);
    }

    @Override
    public void deleteHotelByID(Long id) {
        log.info("Deleting hotel by id: {}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel Details not found with ID: "+id));

        for (Room room:hotel.getRooms()){
            inventoryService.deleteFutureInventory(room);
        }
        hotelRepository.delete(hotel);
    }

    @Override
    public void activateHotel(Long id) {
        log.info("Activating hotel with hotel id: {}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel Details not found with ID: "+id));
        hotel.setIsActive(true);
        hotel = hotelRepository.save(hotel);

        for (Room room:hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }


    }

    @Override
    public List<HotelDto> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map((element) -> modelMapper.map(element, HotelDto.class))
                .toList();
    }
}
