package com.dawan.gquizz;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.ScoreRepository;
import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.IQuestionService;

import com.dawan.gquizz.services.ScoreService;
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ScoreService scoreService;

    public static void main(String[] args) {
        SpringApplication.run(GquizzApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Category catSport = categoryRepository.saveAndFlush(new Category().setLabel("sport"));
//        Category catSport = categoryRepository.findByLabel("sport");
        Category catMusique = categoryRepository.saveAndFlush(new Category().setLabel("musique"));
        Category catTvCinema = categoryRepository.saveAndFlush(new Category().setLabel("tv_cinema"));
        Category catActuPolitique = categoryRepository.saveAndFlush(new Category().setLabel("actu_politique"));
        Category catArtLitterature = categoryRepository.saveAndFlush(new Category().setLabel("art_litterature"));
        Category catJeuxVideos = categoryRepository.saveAndFlush(new Category().setLabel("jeux_videos"));
        Category catCultureG = categoryRepository.saveAndFlush(new Category().setLabel("culture_generale"));

        User user = userRepository.save(new User().setEmail("luc@gmail.com").setPseudo("Lucx67").setPassword("password123"));
        User user2 = userRepository.save(new User().setEmail("jean@gmail.com").setPseudo("DarkJean").setPassword("1234"));
        User user3 = userRepository.save(new User().setEmail("abc@def.com").setPseudo("Alpha-Bête").setPassword("GrouGrou"));
        scoreRepository.save(new Score().setUser(user).setCategory(catSport).setBestScore(1));
        //scoreRepository.save(new Score().setUser(user2).setCategory(catSport).setBestScore(999));
        //scoreRepository.save(new Score().setUser(user3).setCategory(catMusique).setBestScore(150));

       /* lastQuizzRepository.save(new LastQuizz()
                .setIdQuestions(questionService.getQuizByCategory("jeux_videos").stream().map(QuestionDTO::get_id).toList())
                .setCategory(catJeuxVideos)
                .setUser(userRepository.findById(1L).get()));

        lastQuizzRepository.save(new LastQuizz()
                .setIdQuestions(questionService.getQuizByCategory("sport").stream().map(QuestionDTO::get_id).toList())
                .setCategory(catSport)
                .setUser(userRepository.findById(2L).get()));

        lastQuizzRepository.save(new LastQuizz()
                .setIdQuestions(questionService.getQuizByCategory("tv_cinema").stream().map(QuestionDTO::get_id).toList())
                .setCategory(catTvCinema)*/
//
//        System.out.println(scoreRepository.countByUserId(user2.getId()));
//        System.out.println(scoreRepository.existsByUserIdAndCategoryId(user2.getId(), categoryRepository.findById(1L).get().getId()));

//        try {
//            System.out.println(scoreService.updateBestScore(3L, catSport.getId()));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}