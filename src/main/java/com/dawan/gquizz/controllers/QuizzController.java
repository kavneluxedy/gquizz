package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.services.IQuestionService;
import com.dawan.gquizz.utils.AnswerBody;
import com.dawan.gquizz.utils.JsonHelper;
import com.dawan.gquizz.utils.ScoreHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.QuizzServiceImpl;

import java.util.Optional;
import java.util.stream.Stream;

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
    private LastQuizzRepository lastQuizzRepository;

    @PostMapping(value = "/answer", consumes = {"*/*"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isUserAnswerValid(@RequestBody AnswerBody body) throws Exception {
        out.println("USER Answer : " + body.answer);
        out.println("ID Q : " + body.questionId);
        out.println("USER Email: " + body.userId);

        // Récupère l'utilisateur de la base de données
        User user = userRepository.findById(body.userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        String idQuestion = user.getLastQuizz().getIdQuestions().stream().filter(idQ -> idQ.equals(body.questionId)).toString();
        System.out.println(idQuestion);
        QuestionDTO quiz = questionService.findById(body.questionId);

        // Affiche la question et les réponses dans la console
        System.out.println("Question: " + quiz.getQuestion());
        System.out.println("Bonne réponse: " + quiz.getAnswer());
        System.out.println("Mauvaises réponses: " + String.join(", ", quiz.getBadAnswers()));

        int currentCount = user.getLastQuizz().getIdQuestions().indexOf(body.questionId) + 1;
        if (currentCount >= 10) {
            Score newScore = ScoreHelper.createNewScore(user, user.getLastQuizz().getCategory());
            System.out.println("Congratulations !!" + " ===> " + user.getCurrentScore() + "/" + currentCount);
            // TODO Set New Score
        }

        // Vérifie si l'utilisateur a donné la bonne réponse
        if (body.answer.equals(quiz.getAnswer())) {
            System.out.println("Réponse correcte !");

            // Ajoute +1 au score actuel de l'utilisateur
            user.setCurrentScore(user.getCurrentScore() + 1);
            // Sauvegarde l'utilisateur avec le score actuel mis à jour
            userRepository.save(user);
            System.out.println("Score actuel mis à jour: " + user.getCurrentScore());
            
            return true;
        } else if (body.answer.isEmpty()) {
            throw new RuntimeException("Erreur avec la réponse proposée !");
        } else {
            System.out.println("Réponse incorrecte !");
            return false;
        }
    }

    @GetMapping("/{userId}/{category}")
    public ResponseEntity<Integer> resetUserCurrentScore(@PathVariable Long userId, @PathVariable String category) { //resetUserCurrentScore = startNewQuiz
        Optional<User> user = userRepository.findById(userId).stream().findFirst();
        if (user.isPresent()) {
            user.ifPresent(user1 -> {
                user1.setCurrentScore(0);
                userRepository.saveAndFlush(user1);
            });
            out.println("RESET USER SCORE");
            return new ResponseEntity<>(user.get().getCurrentScore(), HttpStatus.OK);
        }
        return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
    }
}