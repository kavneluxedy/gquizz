package com.dawan.gquizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawan.gquizz.entities.LastQuizz;

import java.util.Optional;

public interface LastQuizzRepository extends JpaRepository<LastQuizz, Long> {

    LastQuizz findByUserEmail(String email);
}
