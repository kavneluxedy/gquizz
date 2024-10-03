package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.QuestionDTO;

import java.util.Set;

//Interface définissant les services liés aux questions
public interface IQuestionService {
 
 // Méthode pour obtenir une question aléatoire d'une catégorie spécifiée
 QuestionDTO getRandomQuestionByCategory(String category) throws Exception;

 // Méthode pour obtenir une question aléatoire sans spécification de catégorie
 QuestionDTO getRandomQuestion() throws Exception;

 // Méthode pour obtenir un ensemble de questions pour un quiz
 Set<QuestionDTO> getQuiz() throws Exception;

 // Méthode pour obtenir un ensemble de questions pour un quiz basé sur une catégorie
 Set<QuestionDTO> getQuizByCategory(String category) throws Exception;

 // Méthode pour trouver une question par son identifiant
 QuestionDTO findById(String id) throws Exception;
}

