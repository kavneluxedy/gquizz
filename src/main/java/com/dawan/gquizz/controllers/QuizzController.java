package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.services.IQuestionService;
import com.dawan.gquizz.services.ScoreService;
import com.dawan.gquizz.utils.AnswerBody;
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

    @PostMapping(value = "/answer", consumes = {"*/*"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isUserAnswerValid(@RequestBody AnswerBody body) throws Exception {

        // Récupère l'utilisateur de la base de données
        User user = userRepository.findById(body.userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        String idQuestion = user.getLastQuizz().getIdQuestions().stream().findFirst().filter(idQ -> idQ.equals(body.questionId)).toString();
        System.out.println("BUG " + idQuestion);
        QuestionDTO quiz = questionService.findById(body.questionId);

        int currentCount = user.getLastQuizz().getIdQuestions().indexOf(body.questionId) + 1;

        checkQuizzFinished(body.userId, currentCount, user);

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

    private void checkQuizzFinished(Long userId, int currentCount, User user) {
        if (currentCount >= 10) {
            scoreService.updateBestScore(userId, 1L);//TODO fix
        }
    }

    @GetMapping("/{userId}/{category}")
    public ResponseEntity<String> startNewQuizz(@PathVariable Long userId, @PathVariable String category) { //resetUserCurrentScore = startNewQuiz
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