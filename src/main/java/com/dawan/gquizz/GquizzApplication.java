package com.dawan.gquizz;

import com.dawan.gquizz.dtos.Quiz;
import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.ScoreRepository;
import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.IQuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@SpringBootApplication

public class GquizzApplication implements CommandLineRunner {

    @Configuration
    public class Config {
        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    @Autowired
    private ScoreRepository scoreRepository;

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

        user = userRepository.save(user);
        user2 = userRepository.save(user2);

        Score newScore = new Score().setUser(user2).setCategory("sport").setBestScore(250);
        scoreRepository.save(newScore);

        //Création lastQuizz
        lastQuizzRepository.save(new LastQuizz()
                .setIdQuestions(questionService.getQuiz().stream().map(Quiz::get_id).toList())
                .setCategory("sport")
                .setUser(user));
    }

    private Score createScore(User user, String category, int bestScore) {
        Score score = new Score();
        score.setUser(user);
        score.setCategory(category);
        score.setBestScore(bestScore);
        return score;
    }
}