package com.omeshwar.project.airBNB.repository;

import com.omeshwar.project.airBNB.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}