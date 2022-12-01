package com.travel.hotelsapp.service;

import com.travel.hotelsapp.filter.HotelFilter;
import com.travel.hotelsapp.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    List<Hotel> findAllHotels();
    List<Hotel> findAllHotelsByCriteria(HotelFilter hotelFilter);
    Page<Hotel> findPaginated(Pageable pageable, HotelFilter hotelFilter);
    List<String> getAllCountries();

    Hotel findHotelById(int id);
}
