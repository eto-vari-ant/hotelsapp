package com.travel.hotelsapp.repository;

import com.travel.hotelsapp.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
