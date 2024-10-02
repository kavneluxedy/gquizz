package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query(value = "SELECT s FROM Score s WHERE s.category = :category ORDER BY s.bestScore DESC")
    Optional<List<Score>> findByCategoryOrderByBestScore(@Param("category") Category category);

    Optional<Score> findOneByUser_IdAndCategory_Id(@Param("userId") Long userId, @Param("categoryId") Long categoryId);
}
