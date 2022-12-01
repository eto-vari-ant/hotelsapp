package com.travel.hotelsapp.repository;

import com.travel.hotelsapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
