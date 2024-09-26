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
        return List.of();
    }

    @Override
    public User getById(Long userId) throws Exception {
        Optional<User> u = userRepository.findById(userId);
        User user = null;
        if (u.isPresent()) {
            user = u.get();
        }
        return user;
    }

    @Override
    public void create(User user) throws Exception {
        userRepository.save(user);
    }

    @Override
    public void update(Long userId, User user) throws Exception {
        if (getById(userId) != null) {
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
