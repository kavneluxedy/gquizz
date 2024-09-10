package com.dawan.gquizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawan.gquizz.entities.User;

public interface IScoresRepository extends JpaRepository<User, String> {

}
