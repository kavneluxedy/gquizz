package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<String> findAllCategory();

    Optional<User> getById(Long userId) throws Exception;

    void create(User user) throws Exception;

	void deleteById(Long userId) throws Exception;

	void update(Long userId, User user) throws Exception;
}
