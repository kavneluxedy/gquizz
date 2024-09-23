package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.QuestionDTO;

import java.util.Set;

public interface IQuestionService {
    QuestionDTO getRandomQuestionByCategory(String category) throws Exception;

    QuestionDTO getRandomQuestion() throws Exception;

    Set<QuestionDTO> getQuiz() throws Exception;

    Set<QuestionDTO> getQuizByCategory(String category) throws Exception;

    QuestionDTO findById(String id) throws Exception;
}
