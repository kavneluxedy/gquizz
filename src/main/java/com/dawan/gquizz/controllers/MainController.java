package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MainController {

	// Injection des services et repositories nécessaires pour la gestion des utilisateurs, questions, catégories, etc.
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuestionServiceImpl questionService;

	@Autowired
	private LastQuizzRepository lastQuizzRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private LastQuizzServiceImpl lastQuizzService;

	@Autowired
	private NewQuizzService newQuizzService;

	// Endpoint pour récupérer toutes les catégories
	@GetMapping(path = "/getAllCategories")
	public List<Category> getAllCategories() {
	    return categoryService.findAllCategories(); // Appel au service des catégories pour récupérer toutes les catégories
	}

	// Endpoint pour récupérer un quiz par catégorie
	@GetMapping(path = "/quiz/{categoryLabel}", produces = "application/json")
	public Set<QuestionDTO> getQuizByCategory(@PathVariable String categoryLabel) throws Exception {
	    return questionService.getQuizByCategory(categoryLabel); // Appel au service pour obtenir un quiz basé sur une catégorie
	}

	// Endpoint pour récupérer un quiz spécifique à un utilisateur et une catégorie
	@GetMapping(path = "/quiz/{userId}/{categoryLabel}", produces = "application/json")
	public Set<QuestionDTO> getQuizByCategory(@PathVariable Long userId, @PathVariable String categoryLabel) throws Exception {
	    // Récupération du quiz en fonction de la catégorie
	    Set<QuestionDTO> questions = questionService.getQuizByCategory(categoryLabel);

	    // Copie des questions pour supprimer les réponses avant de les retourner
	    Set<QuestionDTO> questionsWithoutAnswer = new HashSet<>(questions);

	    // Pour chaque question, on supprime la réponse et les mauvaises réponses
	    for (QuestionDTO q : questionsWithoutAnswer) {
	        q.setAnswer(""); // On vide la réponse
	        q.setBadAnswers(new ArrayList<>(4)); // On vide les mauvaises réponses
	    }

	    // Prépare une liste d'identifiants des questions
	    List<String> idQuestions = new ArrayList<>();
	    questions.forEach(questionDTO -> {
	        idQuestions.add(questionDTO.get_id());
	        // Affiche des informations sur les questions pour le débogage
	        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
	        System.out.println(questionDTO.get_id());
	        System.out.println(questionDTO.getAnswer());
	        System.out.println(questionDTO.getCategory());
	        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
	    });

	    // Récupération de l'utilisateur par son ID
	    Optional<User> optUser = userRepository.findById(userId);
	    optUser.map(user -> {
	        // Si un quiz précédent existe pour l'utilisateur, il est mis à jour, sinon un nouveau est créé
	        lastQuizzRepository.findByUserId(user.getId()).map(lastQuizz -> {
	            // Mise à jour du dernier quiz de l'utilisateur avec la nouvelle catégorie et les nouvelles questions
	            lastQuizzRepository.save(lastQuizz
	                .setCategory(categoryRepository.findByLabel(categoryLabel))
	                .setUser(user)
	                .setIdQuestions(idQuestions));
	            return lastQuizz;
	        }).orElseGet(() -> {
	            // Si aucun quiz précédent n'existe, on crée un nouveau quiz pour l'utilisateur
	            return lastQuizzRepository.save(
	                new LastQuizz()
	                    .setCategory(categoryRepository.findByLabel(categoryLabel))
	                    .setUser(user)
	                    .setIdQuestions(idQuestions)
	                    .setCurrentCount(0));
	        });

	        // Réinitialisation du score de l'utilisateur pour le nouveau quiz
	        newQuizzService.resetCurrentScore(user);
	        return user;
	    }).orElseThrow(); // Si l'utilisateur n'est pas trouvé, une exception est levée

	    // Retourne la liste des questions sans réponses
	    return questionsWithoutAnswer;
	}

	// Endpoint pour récupérer une question aléatoire
	@GetMapping(path = "/all", produces = "application/json")
	public QuestionDTO getRandomQuestion(@RequestParam(value = "count", required = false) Integer count) throws Exception {
	    return questionService.getRandomQuestion(); // Appel au service pour obtenir une question aléatoire
	}

	// Endpoint pour récupérer une question aléatoire basée sur la catégorie "sport"
	@GetMapping(path = "/cat", produces = "application/json")
	public QuestionDTO getRandomQuestionByCategory() throws Exception {
	    return questionService.getRandomQuestionByCategory("sport"); // Appel au service pour une question aléatoire dans la catégorie "sport"
	}

	// Endpoint pour récupérer un quiz complet
	@GetMapping(path = "/quiz", produces = "application/json")
	public Set<QuestionDTO> getQuiz() throws Exception {
	    return questionService.getQuiz(); // Appel au service pour obtenir un quiz complet
	}

}