package com.omeshwar.project.airBNB.dto;

import com.omeshwar.project.airBNB.entity.Guest;
import com.omeshwar.project.airBNB.entity.enums.BookingStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer roomCounts;
    private BookingStatus status;
    private Set<Guest> guests;
    private LocalDateTime createdAt;
    private BigDecimal amount;
}
