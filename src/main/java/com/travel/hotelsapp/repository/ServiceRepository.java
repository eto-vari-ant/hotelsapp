package com.travel.hotelsapp.repository;

import com.travel.hotelsapp.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer>

{
}
