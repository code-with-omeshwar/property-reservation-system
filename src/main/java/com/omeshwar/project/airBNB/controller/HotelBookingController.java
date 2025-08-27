package com.omeshwar.project.airBNB.controller;

import com.omeshwar.project.airBNB.dto.BookingDto;
import com.omeshwar.project.airBNB.dto.BookingRequest;
import com.omeshwar.project.airBNB.dto.GuestDto;
import com.omeshwar.project.airBNB.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
@Slf4j
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
        log.info("Controller to initialise booking");
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtoList){
        log.info("Adding guests controller");
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }

}
