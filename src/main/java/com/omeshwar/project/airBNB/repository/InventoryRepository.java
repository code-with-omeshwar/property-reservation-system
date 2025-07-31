package com.omeshwar.project.airBNB.repository;

import com.omeshwar.project.airBNB.entity.Inventory;
import com.omeshwar.project.airBNB.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}