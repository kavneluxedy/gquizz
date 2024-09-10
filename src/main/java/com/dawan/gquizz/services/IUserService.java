package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;

public interface IUserService {
    void create(User user);
    void update(User user);
    void deleteById(String email);
}
