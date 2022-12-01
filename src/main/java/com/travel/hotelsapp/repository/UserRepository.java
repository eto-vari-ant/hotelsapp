package com.travel.hotelsapp.repository;

import com.travel.hotelsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int id);
    User findUserByEmail(String email);
}
