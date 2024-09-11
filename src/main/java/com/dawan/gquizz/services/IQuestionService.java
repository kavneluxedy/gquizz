package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.QuestionDTO;

public interface IQuestionService {
    String generateRandomQuestionByCategory(String category);

    String generateRandomQuestion();
}
