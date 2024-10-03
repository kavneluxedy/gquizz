package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.services.*;
import com.dawan.gquizz.utils.AnswerBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dawan.gquizz.repositories.UserRepository;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/quiz")
public class QuizzController {

    @Autowired
    private QuizzServiceImpl quizzService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreServiceImpl scoreService;

    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewQuizzService newQuizzService;

    @PostMapping(value = "/answer", consumes = {"*/*"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isUserAnswerValid(@RequestBody AnswerBody body) throws Exception {

        // Récupere l'utilisateur 
        Optional<User> u = userRepository.findById(body.userId);
        QuestionDTO quiz = questionService.findById(body.questionId);

        if (body.answer.isEmpty()) {
            // Réponse vide
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        }

        return u.map(user -> {
            // Contrôle si la reponse de l'utilisateur est correcte
            if (body.answer.equals(quiz.getAnswer())) {
                // Bonne réponse
                user.getLastQuizz().setCurrentCount(user.getLastQuizz().getCurrentCount() + 1).setCurrentScore(user.getLastQuizz().getCurrentScore() + 1);
                userRepository.saveAndFlush(user);
                System.out.println("\\\\\\\\\\\\\\\\\\|Bonne réponse/////////////////////////");
                System.out.println("Score actuel ===> " + user.getLastQuizz().getCurrentScore() + "/" + user.getLastQuizz().getCurrentCount());
                return ResponseEntity.ok(true);
            } else {
                // Mauvaise réponse
                user.getLastQuizz().setCurrentCount(user.getLastQuizz().getCurrentCount() + 1);
                userRepository.saveAndFlush(user);
                System.out.println("\\\\\\\\\\\\\\\\\\|Mauvaise réponse/////////////////////////");
                System.out.println("Score actuel ===> " + user.getLastQuizz().getCurrentScore() + "/" + user.getLastQuizz().getCurrentCount());
                return ResponseEntity.ok(false);
            }
        }).get();
    }

    @GetMapping("/{userId}/isQuizzFinished")
    public boolean isQuizzFinished(@PathVariable("userId") Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> {
            System.out.println(u.getLastQuizz().getCurrentCount() >= 10 && u.getLastQuizz().getCurrentScore() <= u.getLastQuizz().getCurrentCount());
            // ! On met à jour le meilleur score ET on reset le score temporaire
            scoreService.updateBestScore(u);
            // Si c'est la fin du quizz
            return u.getLastQuizz().getCurrentCount() >= 10 && u.getLastQuizz().getCurrentScore() <= u.getLastQuizz().getCurrentCount();
        }).get();
    }
}