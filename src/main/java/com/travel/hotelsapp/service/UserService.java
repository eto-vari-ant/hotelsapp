package com.travel.hotelsapp.service;

import com.travel.hotelsapp.model.User;

public interface UserService {
    boolean isEmailExist(String email);

    boolean save(User user);
}
