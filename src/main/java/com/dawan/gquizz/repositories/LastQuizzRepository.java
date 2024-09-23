package com.dawan.gquizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawan.gquizz.entities.LastQuizz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LastQuizzRepository extends JpaRepository<LastQuizz, Long>{
    @Override
    Optional<LastQuizz> findById(Long id);

    @Query("SELECT q FROM LastQuizz q WHERE q.id = :id")
    public LastQuizz getLastQuizById(@Param("id") Long id);
}
