package com.omeshwar.project.airBNB.repository;

import com.omeshwar.project.airBNB.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}