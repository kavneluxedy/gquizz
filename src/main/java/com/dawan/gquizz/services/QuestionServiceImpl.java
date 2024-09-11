package com.dawan.gquizz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private String API_URL = "https://quizzapi.jomoreschi.fr/api/v1/quiz";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String generateRandomQuestion() {
        try {
            String resp = restTemplate.getForObject(API_URL, String.class);

            System.out.println(resp);
            return x[0];
        } catch (RestClientException e) {
            // Gérer l'exception
            return "Erreur lors de l'appel à l'API : " + e.getMessage();
        }
    }

    @Override
    public String generateRandomQuestionByCategory(String category) {
        try {
            return restTemplate.getForObject(API_URL + "?category=" + category, String.class);
        } catch (RestClientException e) {
            // Gérer l'exception
            return "Erreur lors de l'appel à l'API : " + e.getMessage();
        }
    }
}
