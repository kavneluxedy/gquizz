package com.dawan.gquizz.repositories;


import com.dawan.gquizz.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Hérite de JpaRepository pour bénéficier des méthodes CRUD de base sur l'entité User

    // Surcharge de la méthode findById pour retourner un Optional d'un utilisateur par son ID
    @Override
    Optional<User> findById(Long userId);
    
    // Méthode personnalisée pour trouver un utilisateur par son adresse email
    User findByEmail(String email);
}

