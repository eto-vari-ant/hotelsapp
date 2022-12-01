package com.travel.hotelsapp.repository;

import com.travel.hotelsapp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
