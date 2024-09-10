package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class UserServiceImpl implements IUserService {
    @Autowired
    public IUserRepository userRepository;

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(String email, User user) {

    }

    @Override
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(String email) {
        userRepository.deleteById(email);
    }
}
