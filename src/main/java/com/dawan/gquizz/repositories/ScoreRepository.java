package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    // Hérite de JpaRepository pour bénéficier des méthodes CRUD de base sur l'entité Score

    // Requête personnalisée pour obtenir une liste de scores d'une catégorie spécifique,
    // triée par le meilleur score dans l'ordre décroissant (du plus élevé au plus faible)
    @Query(value = "SELECT s FROM Score s WHERE s.category = :category ORDER BY s.bestScore DESC")
    Optional<List<Score>> findByCategoryOrderByBestScore(@Param("category") Category category);

    // Méthode pour récupérer un score spécifique en fonction de l'ID de l'utilisateur et de l'ID de la catégorie
    Optional<Score> findFirstByUser_IdAndCategory_Id(@Param("userId") Long userId, @Param("categoryId") Long categoryId);
}

