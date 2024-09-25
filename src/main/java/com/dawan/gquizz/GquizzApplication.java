package com.dawan.gquizz;

import com.dawan.gquizz.dtos.QuestionDTO;
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
        User user = userRepository.save(new User().setEmail("luc@gmail.com").setPseudo("Lucx67").setPassword("password123"));
        User user2 = userRepository.save(new User().setEmail("jean@gmail.com").setPseudo("DarkJean").setPassword("1234"));
        scoreRepository.save(new Score().setUser(user).setCategory("tv_cinema").setBestScore(120));
        scoreRepository.save(new Score().setUser(user2).setCategory("sport").setBestScore(250));

        lastQuizzRepository.save(new LastQuizz()
                .setIdQuestions(questionService.getQuiz().stream().map(QuestionDTO::get_id).toList())
                .setCategory("sport")
                .setUser(user));
    }
}