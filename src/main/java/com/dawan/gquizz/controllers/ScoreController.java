package com.dawan.gquizz.controllers;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.IUserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class ScoreController {

    @Autowired
    private IUserRepository userRepository;

    @Transactional
    @GetMapping("/score/{category}/{email}")
    public String updateBestScore(@PathVariable String category, @PathVariable String email) {
        System.out.println("ENTER");

        // Récupère l'utilisateur par email
        Optional<User> u = userRepository.findById(email);
        if (u.isPresent()) {
            User user = u.get();
            System.out.println("User found: " + user.getEmail());

            // Cherche le score dans la catégorie donnée
            Score scoreByCategory = user.getScores().stream()
                .filter(score -> score.getCategory().equals(category))  // Utilisez equals pour comparer les catégories
                .findFirst()
                .orElse(null);

            if (scoreByCategory != null) {
                System.out.println("Current score for category " + category + ": " + scoreByCategory.getBestScore());
                System.out.println("User's current score: " + user.getCurrentScore());

                // Vérifie si le score actuel est supérieur au score enregistré
                if (user.getCurrentScore() > scoreByCategory.getBestScore()) {
                    scoreByCategory.setBestScore(user.getCurrentScore()); // Met à jour le score
                    userRepository.save(user); // Sauvegarde l'utilisateur avec le score mis à jour
                    System.out.println("Score updated for category " + category + ": " + user.getCurrentScore());
                } else {
                    System.out.println("No update: Current score is not higher.");
                }
            } else {
                System.out.println("No score found for category: " + category);
            }
        } else {
            System.out.println("User not found");
        }
        return "Score processing completed";
    }
}