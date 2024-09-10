package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT s FROM Score s WHERE s.category = :category ORDER BY s.score DESC")
    List<Score> findTopScoreByCategory(@Param("category") String category);
}
