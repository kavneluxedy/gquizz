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
    public ResponseEntity<Integer> isUserAnswerValid(@RequestBody AnswerBody body) throws Exception {

        // Récupere l'utilisateur 
        Optional<User> u = userRepository.findById(body.userId);
        QuestionDTO quiz = questionService.findById(body.questionId);

        if (body.answer.isEmpty()) {
            // Réponse vide
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        }

        // Contrôle si la reponse de l'utilisateur est correcte
        if (body.answer.equals(quiz.getAnswer())) {
            // Bonne réponse
            return u.map(user -> {
                user.getLastQuizz().setCurrentCount(user.getLastQuizz().getCurrentCount() + 1).setCurrentScore(user.getLastQuizz().getCurrentScore() + 1);
                userRepository.saveAndFlush(user);
                System.out.println("\\\\\\\\\\\\\\\\\\|Bonne réponse/////////////////////////");
                System.out.println("Score actuel ===> " + user.getLastQuizz().getCurrentScore() + "/" + user.getLastQuizz().getCurrentCount());
                return ResponseEntity.ok(u.map(usr -> isQuizzFinished(usr, usr.getLastQuizz().getCurrentCount())).get());
            }).orElseThrow();
        } else {
            // Mauvaise réponse
            return u.map(user -> {
                user.getLastQuizz().setCurrentCount(user.getLastQuizz().getCurrentCount() + 1);
                userRepository.saveAndFlush(user);
                System.out.println("\\\\\\\\\\\\\\\\\\|Mauvaise réponse/////////////////////////");
                System.out.println("Score actuel ===> " + user.getLastQuizz().getCurrentScore() + "/" + user.getLastQuizz().getCurrentCount());
                return ResponseEntity.ok(u.map(usr -> isQuizzFinished(usr, usr.getLastQuizz().getCurrentCount())).get());
            }).orElseThrow();
        }
    }

    private int isQuizzFinished(User user, int currentCount) {
        // Si c'est la fin du quizz
        if (currentCount >= 10) {
            // ! On met à jour le meilleur score AVANT de reset le score temporaire
            return scoreService.updateBestScore(user).getBestScore();
            // On reset le score temporaire (Last Quizz Score)
        }

        return user.getLastQuizz().getCurrentScore();
    }
}

//TODO
//            if (u.isEmpty()) {
//                // Si l'utilisateur n'est pas trouvé propose de créer un compte
//                Map<String, String> response = new HashMap<>();
//                response.put("message", "Utilisateur non trouvé. Est-ce que vous voulez créer un compte?");
//                response.put("redirectUrl", "/create-account");
//                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//            }