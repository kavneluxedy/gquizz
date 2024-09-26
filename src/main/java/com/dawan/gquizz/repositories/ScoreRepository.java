package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query("SELECT s FROM Score s WHERE s.id = :id ORDER BY s.bestScore DESC")
    List<Score> findTopScoreByCategory(@Param("id") Long id);

    Boolean existsByUserIdAndCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    Long countByUserId(@Param("userId") Long userId);

    Score findOneByUser_IdAndCategory_Id(@Param("userId") Long userId, @Param("categoryId") Long categoryId);
}
