package com.omeshwar.project.airBNB.entity;

import com.omeshwar.project.airBNB.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id",nullable = false)
    private Hotel hotel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @Column(nullable = false)
    private LocalDate checkInDate;
    @Column(nullable = false)
    private LocalDate checkOutDate;
    @Column(nullable = false)
    private Integer roomCounts;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
    @Column(nullable = false, precision = 12,scale = 2)
    private BigDecimal amount;
    @ManyToMany
    @JoinTable(
            name = "booking_guest",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> guests;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
