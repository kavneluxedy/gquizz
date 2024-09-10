package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    void create(User user);
    void update(String email, User user);

    void update(User user);

    void deleteById(String email);
}
