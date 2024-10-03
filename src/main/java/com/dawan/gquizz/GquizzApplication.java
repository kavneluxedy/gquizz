package com.dawan.gquizz;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.ScoreRepository;
import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.IQuestionService;

import com.dawan.gquizz.services.ScoreServiceImpl;
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
    public static class Config {

        // Déclare un bean RestTemplate qui sera utilisé pour faire des appels HTTP dans l'application
        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }
    }

    // Injecte l'instance de UserRepository pour interagir avec la base de données des utilisateurs
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IQuestionService questionService; // Gestion des questions

    @Autowired
    private LastQuizzRepository lastQuizzRepository; // Gestion des derniers quiz joués

    @Autowired
    private ScoreRepository scoreRepository; // Gestion des scores des utilisateurs

    @Autowired
    private CategoryRepository categoryRepository; // Gestion des catégories

    @Autowired
    private ScoreServiceImpl scoreService; // Service pour la gestion des scores

    // Point d'entrée de l'application Spring Boot
    public static void main(String[] args) {
        SpringApplication.run(GquizzApplication.class, args); // Lancement de l'application
    }

    // Méthode exécutée après le démarrage de l'application, actuellement vide
    @Override
    public void run(String... args) throws Exception {
        Category catSport = categoryRepository.save(new Category().setLabel("sport"));
        Category catMusique = categoryRepository.save(new Category().setLabel("musique"));
        Category catTvCinema = categoryRepository.save(new Category().setLabel("tv_cinema"));
        Category catActuPolitique = categoryRepository.save(new Category().setLabel("actu_politique"));
        Category catArtLitterature = categoryRepository.save(new Category().setLabel("art_litterature"));
        Category catJeuxVideos = categoryRepository.save(new Category().setLabel("jeux_videos"));
        Category catCultureG = categoryRepository.save(new Category().setLabel("culture_generale"));

        User user = userRepository.save(new User().setEmail("luc@gmail.com").setPseudo("Lucx67").setPassword("password123"));
        User user2 = userRepository.save(new User().setEmail("jean@gmail.com").setPseudo("DarkJean").setPassword("1234"));
        User user3 = userRepository.save(new User().setEmail("abc@def.com").setPseudo("Alpha-Bête").setPassword("GrouGrou"));
//        System.out.println(scoreRepository.findFirstByUser_IdAndCategory_Id(2L, 1L).get());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
    }
}