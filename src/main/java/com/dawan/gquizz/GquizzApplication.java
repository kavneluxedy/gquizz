package com.dawan.gquizz;

import com.dawan.gquizz.entities.Scores;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@SpringBootApplication
public class GquizzApplication implements CommandLineRunner {

    @Autowired
    private IUserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(GquizzApplication.class, args);
    }

    public enum CATEGORY {tv_cinema, art_litterature, musique, sport, actu_politique, culture_generale}
    
    @Override
    public void run(String... args) throws Exception {
    	// Créer un utilisateur
        User user = new User();
        user.setEmail("luc@gmail.com");
        user.setPseudo("Lucx67");
        user.setPassword("password123");

        // Créer des scores pour chaque catégorie
        Set<Scores> scores = new HashSet<>();
        scores.add(createScore(user, "tv_cinema", 100));
        scores.add(createScore(user, "art_litterature", 50));
        scores.add(createScore(user, "musique", 75));
        scores.add(createScore(user, "sport", 60));
        scores.add(createScore(user, "politique", 80));
        scores.add(createScore(user, "culture_generale", 90));

        user.setScores(scores);

        // Sauvegarder l'utilisateur
        userRepository.save(user);

        System.out.println("Utilisateur avec scores enregistré avec succès !");
    }

    private Scores createScore(User user, String category, int scoreValue) {
        Scores score = new Scores();
        score.setUser(user);
        score.setCategory(category);
        score.setScore(scoreValue);
        return score;
    }
 }