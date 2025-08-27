package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.dto.HotelDto;
import com.omeshwar.project.airBNB.dto.HotelSearchRequest;
import com.omeshwar.project.airBNB.entity.Hotel;
import com.omeshwar.project.airBNB.entity.Inventory;
import com.omeshwar.project.airBNB.entity.Room;
import com.omeshwar.project.airBNB.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryServiceImp implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        while (!today.isAfter(endDate)){
            Inventory inventory = Inventory
                    .builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .totalCount(room.getTotalCount())
                    .surgeFactor(BigDecimal.ONE)
                    .price(room.getBasePrice())
                    .closed(false)
                    .bookedCount(0)
                    .reservedCount(0)
                    .build();
            inventoryRepository.save(inventory);
            today = today.plusDays(1);
        }
    }

    @Override
    public void deleteInventories(Room room) {
        log.info("Deleting all future inventories for room with ID: {}",room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        Pageable pageable = PageRequest.of(hotelSearchRequest.getPageNumber(), hotelSearchRequest.getPageSize());

        Long dateCount = ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate()) + 1L;

        Page<Hotel> hotelPage = inventoryRepository.findHotelsWithAvailableInventory(
                hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(), hotelSearchRequest.getRoomsCount(),
                dateCount, pageable);

        return hotelPage.map(hotel -> modelMapper.map(hotel,HotelDto.class));
    }
}
