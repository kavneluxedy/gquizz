package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.ApiQuizDTO;
import com.dawan.gquizz.dtos.QuizzDTO;
import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private Random rng = new Random();
    private String API_URL = "https://quizzapi.jomoreschi.fr/api/v1/quiz";

    @Lazy
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public QuestionDTO getRandomQuestion() throws Exception {
        try {
            QuizzDTO response = restTemplate.getForObject(API_URL, QuizzDTO.class);
            int count = response.getCount();
            return response.getQuizzes()[rng.nextInt(0, count)];
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public QuestionDTO getRandomQuestionByCategory(String category) throws Exception {
        QuizzDTO response = restTemplate.getForObject(API_URL + "?category=" + category, QuizzDTO.class);
        int count = response.getCount();
        List<QuestionDTO> questions = Arrays.stream(response.getQuizzes()).filter(quiz -> Objects.equals(quiz.getCategory(), category)).toList();
        return questions.get(rng.nextInt(0, count));
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
        Set<QuestionDTO> list = new HashSet<>();

        while (list.size() < 10) {
            QuestionDTO question = getRandomQuestionByCategory(category);

            if (!list.isEmpty()) {
                if (!list.contains(question)) {
                    //TODO FIX NON-DISTINCTS
//                    System.out.println("le set ne contient pas de doublons");
                    list.add(question);
                } else {
//                    System.out.println("le set contient des doublons");
                }
            } else {
                list.add(question);
            }

            List<String> allAnswers = new ArrayList<>(question.getBadAnswers());
            allAnswers.add(question.getAnswer());
            question.setAllAnswers(allAnswers);
        }
        return list;
    }

    @Override
    public QuestionDTO findById(String id) throws Exception {
        ApiQuizDTO response = restTemplate.getForObject(API_URL + "/" + id, ApiQuizDTO.class);
        if (response != null)
            return response.getQuiz();
        return null;
    }
}
