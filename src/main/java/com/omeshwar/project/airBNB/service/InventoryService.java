package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);
    void deleteInventories(Room room);

}
