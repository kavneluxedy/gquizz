package com.dawan.gquizz;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;


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

        // Créer et attribuer des scores
        Map<String, Integer> scores = new HashMap<>();
        scores.put(CATEGORY.tv_cinema.name(), 100);
        scores.put(CATEGORY.actu_politique.name(), 50);
        scores.put(CATEGORY.art_litterature.name(), 20);
        scores.put(CATEGORY.musique.name(), 60);
        scores.put(CATEGORY.culture_generale.name(), 70);
        scores.put(CATEGORY.sport.name(), 10);
        
        user.setScores(scores);

        // Sauvegarder l'utilisateur dans la base de données
        userRepository.save(user);

        System.out.println("Utilisateur enregistré avec succès !");
    }
}