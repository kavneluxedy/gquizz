package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    public IUserRepository userRepository;

    @Override
    public List<String> findAllCategory() {
        return userRepository.findAllCategory();
    }

    @Override
    public Optional<User> getByEmail(String email) throws Exception {
        Optional<User> user = userRepository.findById(email);
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
    public void update(String email, User user) throws Exception {
        Optional<User> u = getByEmail(email);
        if (u.isPresent()) {
            userRepository.saveAndFlush(user);
        } else {
            create(user);
        }
    }

    @Override
    public void deleteById(String email) throws Exception {
        userRepository.deleteById(email);
    }
}
