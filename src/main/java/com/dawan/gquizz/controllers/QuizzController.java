package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.services.IQuestionService;
import com.dawan.gquizz.utils.JsonHelper;
import com.dawan.gquizz.utils.ScoreHelper;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.QuizzServiceImpl;

import static java.lang.System.*;

@RestController
@RequestMapping(value = "/quiz")
public class QuizzController {

    @Autowired
    private QuizzServiceImpl quizzService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    @PostMapping(value = "/answer", consumes = {"*/*"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> isUserAnswerValid(@JsonProperty("answer") String answer, @JsonProperty("email") String email, @JsonProperty("id") String id) throws Exception {
        out.println("USER Answer : " + answer);
        out.println("ID Q : " + id);
        out.println("USER Email: " + email);

        // Récupère l'utilisateur de la base de données
        User user = userRepository.findById(email).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        String idQuestion = user.getLastQuizz().getIdQuestions().stream().filter(idQ -> idQ.equals(id)).toString();
        System.out.println(idQuestion);
        QuestionDTO quiz = questionService.findById(id);

        // Affiche la question et les réponses dans la console
        System.out.println("Question: " + quiz.getQuestion());
        System.out.println("Bonne réponse: " + quiz.getAnswer());
        System.out.println("USER Answer : " + answer);
        System.out.println("Mauvaises réponses: " + String.join(", ", quiz.getBadAnswers()));

        int currentCount = user.getLastQuizz().getIdQuestions().indexOf(id) + 1;
        if (currentCount >= 10) {
            Score newScore = ScoreHelper.createNewScore(user, user.getLastQuizz().getCategory());
            System.out.println("Congratulations !!" + " ===> " + user.getCurrentScore() + "/" + currentCount);
            // TODO Set New Score
        }

        // Vérifie si l'utilisateur a donné la bonne réponse
        if (answer.equals(quiz.getAnswer())) {
            System.out.println("Réponse correcte !");

            // Ajoute +1 au score actuel de l'utilisateur
            user.setCurrentScore(user.getCurrentScore() + 1);

            // Sauvegarde l'utilisateur avec le score actuel mis à jour
            userRepository.save(user);
            System.out.println("Score actuel mis à jour: " + user.getCurrentScore());
            return new ResponseEntity<>(JsonHelper.serialize("GOOD"), HttpStatus.OK);
        } else if (answer.isEmpty()) {
            throw new RuntimeException("Erreur avec la réponse proposée !");
        } else {
            System.out.println("Réponse incorrecte !");
            return new ResponseEntity<>(JsonHelper.serialize("BAD"), HttpStatus.OK);
        }
    }
}