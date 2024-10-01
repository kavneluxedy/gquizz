package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.LastQuizz;
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

import java.util.HashMap;
import java.util.Map;
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

    @PostMapping(value = "/answer", consumes = {"*/*"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> isUserAnswerValid(@RequestBody AnswerBody body) throws Exception {

        // Récupere l'utilisateur 
        Optional<User> u = userRepository.findById(body.userId);
        QuestionDTO quiz = questionService.findById(body.questionId);
        User user = null;
        LastQuizz lq = null;

        // Contrôle si l'utilisateur existe
        if (u.isPresent()) {
            user = u.get();
            lq = user.getLastQuizz();
        } else {
            // Si l'utilisateur n'est pas trouvé propose de créer un compte
            Map<String, String> response = new HashMap<>();
            response.put("message", "Utilisateur non trouvé. Est-ce que vous voulez créer un compte?");
            response.put("redirectUrl", "/create-account");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        //TODO Gérer la validation de réponse pour un utilisateur non-authentifié
        // Car lq.getIdQuestions is null si pas de user authentifié
        if (lq == null)
            lq = lastQuizzRepository.save(new LastQuizz().setUser(user).setCategory(categoryRepository.findByLabel(quiz.getCategory()).setLabel(quiz.getCategory())));

        int currentCount = lq.getIdQuestions().indexOf(body.questionId) + 1;
        System.out.println("================>: " + currentCount);

        // Contrôle si la reponse se l'utilisateur est correcte
        if (body.answer.equals(quiz.getAnswer())) {
            System.out.println("Réponse correcte !");

            user.setCurrentScore(user.getCurrentScore() + 1);
            userRepository.saveAndFlush(user);

            return ResponseEntity.ok(true);

        } else if (body.answer.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        } else {
            System.out.println("Réponse incorrecte !");
        }

        System.out.println("Fin de quizz !!" + " Votre score ===> " + user.getCurrentScore() + "/" + currentCount);

        // Contrôle si le quiz est terminé
        return ResponseEntity.ok(Boolean.TRUE.equals(

                isQuizzFinished(user, currentCount)));
    }

    private Boolean isQuizzFinished(User user, int currentCount) {
        if (currentCount >= 10) {
            scoreService.updateBestScore(user);
            return true;
        }
        return false;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> startNewQuizz(@PathVariable Long userId) { //resetUserCurrentScore = startNewQuiz
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> {
            u.setCurrentScore(0);
            userRepository.save(u);
            return new ResponseEntity<>("Reset Current Score de l'utilisateur " + u.getPseudo(), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>("Utilisateur non trouvé ", HttpStatus.NOT_FOUND));
    }
}