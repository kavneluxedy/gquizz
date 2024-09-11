package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.Quiz;

import java.util.List;
import java.util.Set;

public interface IQuestionService {
    Quiz getRandomQuestionByCategory(String category);

    Quiz getRandomQuestion();

    Set<Quiz> getQuiz();

    Set<Quiz> getQuizByCategory(String category);
}
