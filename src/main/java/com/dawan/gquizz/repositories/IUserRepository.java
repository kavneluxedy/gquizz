package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT score FROM Score s WHERE s.score > 1 ORDER BY s.score DESC")
    public List<Integer> getBestScores();
}
