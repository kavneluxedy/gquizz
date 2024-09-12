package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.QuizzDTO;
import com.dawan.gquizz.dtos.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Stream;

@Service
public class QuestionServiceImpl implements IQuestionService {
    private Random rng = new Random();

    private String API_URL = "https://quizzapi.jomoreschi.fr/api/v1/quiz";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Quiz getRandomQuestion() {
        //QuizzDTO response = restTemplate.getForObject(API_URL, QuizzDTO.class);
        //int count = response.count + 1;
        //return response.getQuizzes()[rng.nextInt(0, count)];
    	return new Quiz();
    }

    @Override
    public Quiz getRandomQuestionByCategory(String category) {
        //QuizzDTO response = restTemplate.getForObject(API_URL + "?category=" + category, QuizzDTO.class);
       // int count = response.count;
        //List<Quiz> questions = Arrays.stream(response.getQuizzes()).filter(quiz -> Objects.equals(quiz.getCategory(), category)).toList();
        return new Quiz();//questions.get(rng.nextInt(0, count));
    }

    @Override
    public Set<Quiz> getQuiz() {
        Set<Quiz> list = new HashSet<>();

        while (list.size() < 10) {
            var question = getRandomQuestion();
            if (!list.contains(question)) {
                list.add(question);
            }
        }

        return list;
    }

    @Override
    public Set<Quiz> getQuizByCategory(String category) {
        Set<Quiz> list = new HashSet<>();

        while (list.size() < 10) {
            var question = getRandomQuestionByCategory(category);
            if (!list.contains(question)) {
                list.add(question);
            }
        }

        return list;
    }
}
