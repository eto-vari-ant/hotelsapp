package com.travel.hotelsapp.repository;

import com.travel.hotelsapp.filter.HotelFilter;
import com.travel.hotelsapp.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query(value = "select distinct country from hotels", nativeQuery = true)
    List<String> findAllCountries();

    Hotel findHotelById(int id);
}
