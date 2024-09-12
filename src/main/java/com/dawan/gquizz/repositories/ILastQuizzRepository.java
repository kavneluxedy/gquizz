package com.dawan.gquizz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawan.gquizz.entities.LastQuizz;

public interface  ILastQuizzRepository extends JpaRepository<LastQuizz, Long>{
}
