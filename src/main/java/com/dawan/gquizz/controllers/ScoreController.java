package com.dawan.gquizz.controllers;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.repositories.UserRepository;

import com.dawan.gquizz.services.ScoreService;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreService scoreService;

    @Transactional
    @GetMapping("/{category}/{userId}")
    public String updateBestScore(@PathVariable String category, @PathVariable Long userId) {

        userRepository.findById(userId).ifPresent(
                user -> {
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
                }
        );
        return "Score processing completed";
    }

    @GetMapping("/{userId}/{category}")
    public Score findByCategory(@PathVariable String email, @PathVariable String category) {
        return scoreService.findByEmailAndCategory(email, category);
    }
}