package com.dawan.gquizz;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class GquizzApplication implements CommandLineRunner {

    @Configuration
    public class AppConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @Autowired
    private IUserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(GquizzApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Créer un utilisateur
        User user = new User();
        user.setEmail("luc@gmail.com");
        user.setPseudo("Lucx67");
        user.setPassword("password123");

        User user2 = new User();
        user2.setEmail("jean@gmail.com");
        user2.setPseudo("DarkJean");
        user2.setPassword("1234");

        userRepository.save(user);
        userRepository.save(user2);

        // Créer des scores pour chaque catégorie
        Set<Score> scores = new HashSet<>();
        scores.add(createScore(user, "tv_cinema", 100));
        scores.add(createScore(user, "art_litterature", 50));
        scores.add(createScore(user, "musique", 75));
        scores.add(createScore(user, "sport", 60));
        scores.add(createScore(user, "actu_politique", 80));
        scores.add(createScore(user, "culture_generale", 90));

        Set<Score> scores2 = new HashSet<>();
        scores2.add(createScore(user2, "tv_cinema", 100));
        scores2.add(createScore(user2, "art_litterature", 50));
        scores2.add(createScore(user2, "musique", 75));
        scores2.add(createScore(user2, "sport", 60));
        scores2.add(createScore(user2, "actu_politique", 80));
        scores2.add(createScore(user2, "culture_generale", 90));

        user.setScores(scores);
        user2.setScores(scores2);

        // Sauvegarder l'utilisateur

        userRepository.save(user);
        userRepository.save(user2);
        System.out.println("Utilisateur avec scores enregistré avec succès !");


        List<Score> resp = userRepository.findTopScoreByCategory("sport");
        System.out.println(resp.get(0).getUser().getEmail());

        for (String cat : userRepository.findAllCategory()) System.out.println(cat);
    }

    private Score createScore(User user, String category, int scoreValue) {
        Score score = new Score();
        score.setUser(user);
        score.setCategory(category);
        score.setScore(scoreValue);
        return score;
    }
}