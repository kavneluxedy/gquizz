package com.dawan.gquizz.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dawan.gquizz.dtos.Quiz;

@Service
public class QuizzService {
	
	public Quiz getQuestionById(String id) {
        
		Quiz question = new Quiz();
        question.set_id("642438e068d7ea9aa9527847");
        question.setQuestion("En quelle année a été projeté le premier film ?");
        question.setAnswer("1895");
        question.setBadAnswers(Arrays.asList("1924", "1912", "1904"));
        question.setCategory("tv_cinema");
        question.setDifficulty("facile");

        return question;
    }
}