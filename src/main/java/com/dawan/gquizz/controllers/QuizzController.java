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
    private ScoreService scoreService;

    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(value = "/answer", consumes = {"*/*"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isUserAnswerValid(@RequestBody AnswerBody body) throws Exception {

        // Récupère l'utilisateur de la base de données
        Optional<User> u = userRepository.findById(body.userId);
        QuestionDTO quiz = questionService.findById(body.questionId);
        User user = null;
        LastQuizz lq = null;

        if (u.isPresent()) {
            lq = u.get().getLastQuizz();
            user = u.get();
        } else {
            //TODO Proposer de créer un compte à l'utilisateur (pop-up de redirection)
        }

        if (lq == null)
            lq = lastQuizzRepository.save(new LastQuizz().setUser(user).setCategory(categoryRepository.findByLabel(quiz.getCategory()).setLabel(quiz.getCategory())));

        int currentCount = lq.getIdQuestions().indexOf(body.questionId) + 1;

        System.out.println("================>: " + currentCount);

        // Vérifie si l'utilisateur a donné la bonne réponse
        if (body.answer.equals(quiz.getAnswer())) {
            System.out.println("Réponse correcte !");

            // Ajoute +1 au score actuel de l'utilisateur
            user.setCurrentScore(user.getCurrentScore() + 1);
            // Sauvegarde l'utilisateur avec le score actuel mis à jour
            userRepository.saveAndFlush(user);

            return true;

        } else if (body.answer.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);

        } else {
            System.out.println("Réponse incorrecte !");
        }

        System.out.println("Fin de quizz !!" + " Votre score ===> " + user.getCurrentScore() + "/" + currentCount);

        return Boolean.TRUE.equals(isQuizzFinished(user, currentCount));
    }

    private Boolean isQuizzFinished(User user, int currentCount) {
        if (currentCount >= 10) {
            // Yanis
            System.out.println(user.getCurrentScore());

            scoreService.updateBestScore(user);
            return true;
        }
        return false;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> startNewQuizz(@PathVariable Long userId) { //resetUserCurrentScore = startNewQuiz
        Optional<User> user = userRepository.findById(userId).stream().findFirst();
        if (user.isPresent()) {
            user.ifPresent(user1 -> {
                user1.setCurrentScore(0);
                userRepository.save(user1);
            });

            return new ResponseEntity<>("Reset Current Score of " + user.get().getPseudo(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}