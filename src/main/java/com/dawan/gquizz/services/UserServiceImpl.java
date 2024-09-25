package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    public UserRepository userRepository;

    @Override
    public List<String> findAllCategory() {
        return userRepository.findAllCategory();
    }

    @Override
    public Optional<User> getById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return null;
        }
        return user;
    }

    @Override
    public void create(User user) throws Exception {
        userRepository.save(user);
    }

    @Override
    public void update(Long userId, User user) throws Exception {
        Optional<User> u = getById(userId);
        if (u.isPresent()) {
            userRepository.saveAndFlush(user);
        } else {
            create(user);
        }
    }

    @Override
    public void deleteById(Long userId) throws Exception {
        userRepository.deleteById(userId);
    }
}
