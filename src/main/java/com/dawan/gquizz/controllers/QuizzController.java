package com.dawan.gquizz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dawan.gquizz.dtos.Quiz;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.IUserRepository;
import com.dawan.gquizz.services.QuizzService;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizzController {

    @Autowired
    private QuizzService quizzService;

    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/question/{id}")
    public void checkUserAnswerInConsole(@PathVariable String id, @RequestParam String userAnswer, @RequestParam String userEmail) {
        // Récupère la question simulée
        Quiz question = quizzService.getQuestionById(id);

        // Affiche la question et les réponses dans la console
        System.out.println("Question: " + question.getQuestion());
        System.out.println("Bonne réponse: " + question.getAnswer());
        System.out.println("Mauvaises réponses: " + String.join(", ", question.getBadAnswers()));

        // Vérifie si l'utilisateur a donné la bonne réponse
        boolean isCorrect = userAnswer.equals(question.getAnswer());

        

        if (isCorrect) {
        	// Récupère l'utilisateur de la base de données
        	User user = userRepository.findById(userEmail).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            System.out.println("Réponse correcte !");
            
            // Ajoute +1 au score actuel de l'utilisateur
            user.setCurrentScore(user.getCurrentScore() + 1);

            // Sauvegarde l'utilisateur avec le score actuel mis à jour
            userRepository.save(user);

            System.out.println("Score actuel mis à jour: " + user.getCurrentScore());
        } else {
            System.out.println("Réponse incorrecte !");
        }
    }


    private Score createNewScore(User user, String category) {
        Score newScore = new Score();
        newScore.setUser(user);
        newScore.setCategory(category);
        newScore.setScore(1); // Initialise le score à 1 car la réponse est correcte
        user.getScores().add(newScore);
        return newScore;
    }
}