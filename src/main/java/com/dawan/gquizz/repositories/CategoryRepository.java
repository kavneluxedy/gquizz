package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Hérite de JpaRepository pour bénéficier des méthodes CRUD de base sur l'entité Category

    // Méthode pour compter le nombre total de catégories en base de données
    long count();

    // Méthode pour trouver une catégorie par son label (nom)
    // Utilise @Param pour mapper le paramètre "label" lors de la requête
    Category findByLabel(@Param("label") String label);
}

