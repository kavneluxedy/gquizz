package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<String> findAllCategory();

    Optional<User> getByEmail(String email) throws Exception;

    void create(User user) throws Exception;

    void update(String email, User user) throws Exception;

    void deleteById(String email) throws Exception;
}
