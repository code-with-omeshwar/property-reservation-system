package com.omeshwar.project.airBNB.repository;

import com.omeshwar.project.airBNB.entity.Hotel;
import com.omeshwar.project.airBNB.entity.Inventory;
import com.omeshwar.project.airBNB.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByRoom(Room room);
    @Query(
            """
            SELECT DISTINCT i.hotel
            FROM Inventory i
            WHERE i.city = :city
                AND i.date BETWEEN :startDate AND :endDate
                AND (i.totalCount - i.bookedCount) >= :roomsCount
                AND i.closed=false
            GROUP BY i.hotel, i.room
            HAVING COUNT(i.date) = :dateCount
            """
    )
    Page<Hotel> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );
}