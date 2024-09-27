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

    private Random rng = new Random();
    private String API_URL = "https://quizzapi.jomoreschi.fr/api/v1/quiz";
    private final static int LIMIT = 10;

    @Lazy
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public QuestionDTO getRandomQuestion() throws Exception {
        try {
            QuizzDTO response = restTemplate.getForObject(API_URL, QuizzDTO.class);
            if (response != null) {
                int count = response.getCount();
                return response.getQuizzes()[rng.nextInt(0, count)];
            }
        } catch (Exception ex) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        return null;
    }

    @Override
    public QuestionDTO getRandomQuestionByCategory(String category) throws Exception {
        QuizzDTO response = restTemplate.getForObject(API_URL + "?category=" + category, QuizzDTO.class);
        if (response != null) {
            int count = response.getCount();
            List<QuestionDTO> questions = Arrays.stream(response.getQuizzes()).filter(quiz -> Objects.equals(quiz.getCategory(), category)).toList();
            return questions.get(rng.nextInt(0, count));
        }
        return null;
    }

    @Override
    public Set<QuestionDTO> getQuiz() throws Exception {
        Set<QuestionDTO> list = new HashSet<>();

        while (list.size() < 10) {
            var question = getRandomQuestion();
            if (!list.contains(question)) {
                list.add(question);
            }
        }

        return list;
    }

    @Override
    public Set<QuestionDTO> getQuizByCategory(String category) throws Exception {
        QuizzDTO quizzByCategory = restTemplate.getForObject(API_URL + "?limit=" + LIMIT + "?category=" + category, QuizzDTO.class);
        Set<QuestionDTO> questions = Arrays.stream(quizzByCategory.getQuizzes()).collect(Collectors.toSet());

        questions.forEach(questionDTO -> {
            List<String> allAnswers = new ArrayList<>(); // []

            allAnswers.add(questionDTO.getAnswer()); // ["Roi 1"]
            allAnswers.addAll(questionDTO.getBadAnswers()); // ["Roi 1", "Roi 2", "Roi 3", "Roi 4", ]
            questionDTO.setAllAnswers(allAnswers);
        });

        return questions;
    }

    @Override
    public QuestionDTO findById(String idQuestion) throws Exception {
        ApiQuizDTO response = restTemplate.getForObject(API_URL + "/" + idQuestion, ApiQuizDTO.class);
        if (response != null) return response.getQuiz();
        return null;
    }
}
