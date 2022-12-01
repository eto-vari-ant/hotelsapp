package com.travel.hotelsapp.repository;

import com.travel.hotelsapp.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<Cost, Integer> {
}
