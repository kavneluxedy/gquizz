package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Score findByUserEmailAndCategory(@Param("email") String email, @Param("category") String category);
}
