package com.dawan.gquizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawan.gquizz.entities.LastQuizz;

public interface LastQuizzRepository extends JpaRepository<LastQuizz, Long> {
    LastQuizz findByUserId(Long userId);
}
