package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.services.IQuestionService;
import com.dawan.gquizz.services.ScoreService;
import com.dawan.gquizz.utils.AnswerBody;
import com.dawan.gquizz.utils.ScoreHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.QuizzServiceImpl;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static java.lang.System.*;

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

    @GetMapping("/{email}/{category}")
    public ResponseEntity<Integer> startNewQuizz(@PathVariable String email) {
        Optional<User> user = userRepository.findById(email).stream().findFirst();

        if (user.isPresent()) {
            user.get().setCurrentScore(0);
            userRepository.saveAndFlush(user.get());
            return new ResponseEntity<>(user.get().getCurrentScore(), HttpStatus.OK);
        } else throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/answer", consumes = {"*/*"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isUserAnswerValid(@RequestBody AnswerBody body) throws Exception {
//        out.println("USER Answer : " + body.answer);
        out.println("ID Q : " + body.id);
//        out.println("USER Email: " + body.email);

        // Récupère l'utilisateur de la base de données
        User user = userRepository.findById(body.email).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        QuestionDTO quiz = questionService.findById(body.id);

        // Affiche la question et les réponses dans la console
//        System.out.println("Question: " + quiz.getQuestion());
//        System.out.println("Bonne réponse: " + quiz.getAnswer());
//        System.out.println("Mauvaises réponses: " + String.join(", ", quiz.getBadAnswers()));

        int currentCount = user.getLastQuizz().getIdQuestions().indexOf(body.id) + 1;
        out.println(currentCount);
        if (currentCount >= 10) {
            Score newScore = ScoreHelper.createNewScore(user, user.getLastQuizz().getCategory());
            scoreService.updateBestScore(body.email, quiz.getCategory(), user.getCurrentScore());
        }

        // Vérifie si l'utilisateur a donné la bonne réponse
        if (body.answer.equals(quiz.getAnswer())) {
            System.out.println("Réponse correcte !");

            // Ajoute +1 au score actuel de l'utilisateur
            user.setCurrentScore(user.getCurrentScore() + 1);
            // Sauvegarde l'utilisateur avec le score actuel mis à jour
            userRepository.save(user);

            System.out.println("FELICITATIONS !!" + " ===> " + user.getCurrentScore() + "/" + currentCount);
            return true;
        } else if (body.answer.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        } else {
            System.out.println("Réponse incorrecte !");
            return false;
        }
    }
}