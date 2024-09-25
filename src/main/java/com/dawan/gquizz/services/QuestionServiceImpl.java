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

@Service
public class QuestionServiceImpl implements IQuestionService {

    private Random rng = new Random();
    private String API_URL = "https://quizzapi.jomoreschi.fr/api/v1/quiz";
    private final int limit = 10;

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
        return Set.of((QuizzDTO) restTemplate.getForObject(API_URL + "?limit=" + limit + "?category=" + category  , QuizzDTO.class));
    }


    @Override
    public QuestionDTO findById(String id) throws Exception {
        ApiQuizDTO response = restTemplate.getForObject(API_URL + "/" + id, ApiQuizDTO.class);
        if (response != null)
            return response.getQuiz();
        return null;
    }
}
