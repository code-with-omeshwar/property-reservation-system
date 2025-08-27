package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.dto.BookingDto;
import com.omeshwar.project.airBNB.dto.BookingRequest;
import com.omeshwar.project.airBNB.dto.GuestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);
    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
