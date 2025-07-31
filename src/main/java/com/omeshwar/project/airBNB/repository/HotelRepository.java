package com.omeshwar.project.airBNB.repository;

import com.omeshwar.project.airBNB.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
