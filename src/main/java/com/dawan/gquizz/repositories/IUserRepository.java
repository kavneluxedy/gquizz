package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT score FROM Score s WHERE s.category = :category ORDER BY s.score DESC LIMIT 10")

    public List<Integer> getBestScoresByCategory(String category);
}
