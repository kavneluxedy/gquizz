package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, String> {
    @Override
    Optional<User> findById(String email);

    @Query(value = "SELECT s FROM Score s WHERE s.category = :category ORDER BY s.bestScore DESC")
    List<Score> findTopScoreByCategory(@Param("category") String category);

    @Query("SELECT DISTINCT category FROM Score s") //TODO Call API Instead of DB
    List<String> findAllCategory();
}
