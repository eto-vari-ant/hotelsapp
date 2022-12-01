package com.travel.hotelsapp.repository;

import com.travel.hotelsapp.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
