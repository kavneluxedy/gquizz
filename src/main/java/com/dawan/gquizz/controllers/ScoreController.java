package com.dawan.gquizz.controllers;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ScoreController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ScoreController scoreController;

    @GetMapping("/score/{category}/{email}")
    public String updateBestScore(@PathVariable String category, @PathVariable String email) {
        System.out.println("ENTER");
        Optional<User> u = userRepository.findById(email);
        if (u.isPresent()) {
            User user = u.get();
            
            Score scoreByCategory = user.getScores().stream().filter(score -> score.getCategory() == category).findFirst().get();

            if (user.getCurrentScore() > scoreByCategory.getBestScore()) {
                Set<Score> scores = new HashSet<>();
                Score score = new Score();
                score.setBestScore(user.getCurrentScore());
                score.setCategory(category);

                User user2 = new User();
                user2.setEmail("jean@gmail.com");
                user2.setPseudo("DarkJean");
                user2.setPassword("1234");

                score.setUser(user2);
                scores.add(score);
                user.setScores(scores);
            }

        } else {
            //TODO Sign Up
        }
        return "Hello World";
    }
}
