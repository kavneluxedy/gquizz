package com.dawan.gquizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawan.gquizz.entities.LastQuizz;

import java.util.Optional;

public interface LastQuizzRepository extends JpaRepository<LastQuizz, Long> {
    // Hérite de JpaRepository pour bénéficier des méthodes CRUD de base sur l'entité LastQuizz

    // Méthode pour trouver le dernier quiz effectué par un utilisateur via son ID
    // Retourne un Optional car il est possible qu'aucun quiz ne soit trouvé pour cet utilisateur
    Optional<LastQuizz> findByUserId(Long userId);
}

