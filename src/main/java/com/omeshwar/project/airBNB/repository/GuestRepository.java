package com.omeshwar.project.airBNB.repository;

import com.omeshwar.project.airBNB.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}