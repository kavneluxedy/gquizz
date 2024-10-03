package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.ApiQuizDTO;
import com.dawan.gquizz.dtos.QuizzDTO;
import com.dawan.gquizz.dtos.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements IQuestionService {

	// Générateur de nombres aléatoires pour sélectionner des questions au hasard
	private Random rng = new Random();

	// URL de l'API pour récupérer les quiz
	private String API_URL = "https://quizzapi.jomoreschi.fr/api/v1/quiz";

	// Limite du nombre de questions à récupérer dans certaines méthodes
	private final static int LIMIT = 10;

	// Injection paresseuse de RestTemplate pour effectuer des appels API
	@Lazy
	@Autowired
	private RestTemplate restTemplate;

	// Récupère une question aléatoire depuis l'API
	@Override
	public QuestionDTO getRandomQuestion() throws Exception {
	    try {
	        // Appel à l'API pour récupérer un ensemble de quiz
	        QuizzDTO response = restTemplate.getForObject(API_URL, QuizzDTO.class);
	        if (response != null) {
	            int count = response.getCount(); // Récupère le nombre de questions disponibles
	            // Sélectionne une question aléatoire parmi celles récupérées
	            return response.getQuizzes()[rng.nextInt(0, count)];
	        }
	    } catch (Exception ex) {
	        // En cas d'erreur lors de l'appel API, lance une exception HTTP 403
	        throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
	    }
	    return null; // Retourne null si aucune réponse n'est obtenue
	}

	// Récupère une question aléatoire en fonction de la catégorie spécifiée
	@Override
	public QuestionDTO getRandomQuestionByCategory(String category) throws Exception {
	    // Appel à l'API avec le paramètre de catégorie
	    QuizzDTO response = restTemplate.getForObject(API_URL + "?category=" + category, QuizzDTO.class);
	    if (response != null) {
	        int count = response.getCount(); // Récupère le nombre de questions de la catégorie
	        // Filtre les questions par catégorie
	        List<QuestionDTO> questions = Arrays.stream(response.getQuizzes())
	            .filter(quiz -> Objects.equals(quiz.getCategory(), category)).toList();
	        // Sélectionne une question aléatoire parmi celles filtrées
	        return questions.get(rng.nextInt(0, count));
	    }
	    return null; // Retourne null si aucune réponse n'est obtenue
	}

	// Récupère un ensemble de questions aléatoires (10 questions uniques)
	@Override
	public Set<QuestionDTO> getQuiz() throws Exception {
	    Set<QuestionDTO> list = new HashSet<>(); // Ensemble pour stocker les questions sans doublons

	    // Continue à ajouter des questions jusqu'à ce que l'ensemble atteigne 10 questions
	    while (list.size() < 10) {
	        var question = getRandomQuestion(); // Récupère une question aléatoire
	        if (!list.contains(question)) { // Vérifie si la question n'est pas déjà dans l'ensemble
	            list.add(question); // Ajoute la question à l'ensemble
	        }
	    }

	    return list; // Retourne l'ensemble de questions uniques
	}

	// Récupère un ensemble de questions par catégorie
	@Override
	public Set<QuestionDTO> getQuizByCategory(String category) throws Exception {
	    // Appel à l'API pour récupérer des quiz limités à la catégorie spécifiée
	    QuizzDTO quizzByCategory = restTemplate.getForObject(API_URL + "?limit=" + LIMIT + "&category=" + category, QuizzDTO.class);
	    // Convertit le tableau de quiz en un ensemble
	    Set<QuestionDTO> questions = Arrays.stream(quizzByCategory.getQuizzes()).collect(Collectors.toSet());

	    // Pour chaque question, mélange les réponses
	    questions.forEach(questionDTO -> {
	        List<String> allAnswers = new ArrayList<>(); // Liste pour stocker toutes les réponses

	        allAnswers.add(questionDTO.getAnswer()); // Ajoute la bonne réponse
	        allAnswers.addAll(questionDTO.getBadAnswers()); // Ajoute les mauvaises réponses
	        Collections.shuffle(allAnswers); // Mélange les réponses
	        questionDTO.setAllAnswers(allAnswers); // Définit la liste mélangée dans l'objet QuestionDTO
	    });

	    return questions; // Retourne l'ensemble de questions avec les réponses mélangées
	}

	// Récupère une question spécifique en fonction de son ID
	@Override
	public QuestionDTO findById(String idQuestion) throws Exception {
	    // Appel à l'API pour récupérer une question spécifique par ID
	    ApiQuizDTO response = restTemplate.getForObject(API_URL + "/" + idQuestion, ApiQuizDTO.class);
	    if (response != null) return response.getQuiz(); // Retourne la question si trouvée
	    return null; // Retourne null si aucune réponse n'est obtenue
	}

}
