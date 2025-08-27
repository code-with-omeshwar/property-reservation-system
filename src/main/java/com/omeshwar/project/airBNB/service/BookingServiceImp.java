package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.dto.BookingDto;
import com.omeshwar.project.airBNB.dto.BookingRequest;
import com.omeshwar.project.airBNB.dto.GuestDto;
import com.omeshwar.project.airBNB.entity.*;
import com.omeshwar.project.airBNB.entity.enums.BookingStatus;
import com.omeshwar.project.airBNB.exception.ResourceNotFoundException;
import com.omeshwar.project.airBNB.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService{
    private final GuestRepository guestRepository;

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {
        log.info("Initialise booking for the given booking request with hotel id {} room id {} date {}-{} room count {}",bookingRequest.getHotelID(),bookingRequest.getRoomID(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());
        Hotel hotel = hotelRepository
                .findById(bookingRequest.getHotelID())
                .orElseThrow(()->new ResourceNotFoundException("Hotel does not exists by ID: "+bookingRequest.getHotelID()));
        Room room = roomRepository
                .findById(bookingRequest.getRoomID())
                .orElseThrow(()->new ResourceNotFoundException("Room does not exists by ID: "+bookingRequest.getRoomID()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(bookingRequest.getRoomID(),
                bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;

        if (inventoryList.size()!=daysCount){
            throw new IllegalStateException("Room is not available anymore");
        }
        //reserve the rooms
        for (Inventory inventory:inventoryList){
            inventory.setReservedCount(inventory.getReservedCount()+bookingRequest.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);
        //create booking

        //TODO: Calculate dynamic amount

        Booking booking = new Booking();
        booking.setCheckInDate(bookingRequest.getCheckInDate());
        booking.setCheckOutDate(bookingRequest.getCheckOutDate());
        booking.setRoom(room);
        booking.setHotel(hotel);
        booking.setStatus(BookingStatus.RESERVED);
        booking.setRoomCounts(bookingRequest.getRoomsCount());
        booking.setUser(getCurrentUser());
        booking.setAmount(BigDecimal.TEN);

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    private User getCurrentUser() {
        //TODO: Remove dummy user
        return userRepository.findById(1L).orElseThrow(()->new ResourceNotFoundException("User does not exists by ID"));
    }

    @Override
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guests for booking with booking ID: {}",bookingId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new ResourceNotFoundException("Booking with id does not exists: "+bookingId));
        if (hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired");
        }
        if (booking.getStatus()!=BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under Reserved State so cannot add guests");
        }
        for (GuestDto guestDto: guestDtoList){
            Guest guest= modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }
}
