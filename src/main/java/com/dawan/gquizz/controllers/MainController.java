package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.services.QuestionServiceImpl;
import com.dawan.gquizz.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MainController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    @GetMapping(path = "/showCategories")
    public List<String> showCategories() {
        return userService.findAllCategory();
    }

    @GetMapping(path = "/all", produces = "application/json")
    public QuestionDTO getRandomQuestion(@RequestParam(value = "count", required = false) Integer count) throws Exception {
        return questionService.getRandomQuestion();
    }

    @GetMapping(path = "/cat", produces = "application/json")
    public QuestionDTO getRandomQuestionByCategory() throws Exception {
        return questionService.getRandomQuestionByCategory("sport");
    }

    @GetMapping(path = "/quiz/{userId}", produces = "application/json")
    public Set<QuestionDTO> getQuiz(@PathVariable Long userId) throws Exception {
        lastQuizzRepository.findByUserId(userId);
        return questionService.getQuiz();
    }

    @GetMapping(path = "/quiz/{userId}/{category}", produces = "application/json")
    public Set<QuestionDTO> getQuizByCategory(@PathVariable Long userId, @PathVariable String category) throws Exception {
        Set<QuestionDTO> questions = questionService.getQuizByCategory(category);

        // Set new Id Questions to User
        List<String> idQuestions = new ArrayList<>();
        questions.forEach(questionDTO -> {
            System.out.println(questionDTO.get_id());
            idQuestions.add(questionDTO.get_id());
        });

        Optional<User> user = userService.getById(userId).stream().findFirst();
        LastQuizz lq = lastQuizzRepository.findByUserId(userId);
        user.ifPresent(u -> lastQuizzRepository.saveAndFlush(lq.setCategory(category).setUser(u).setIdQuestions(idQuestions)));

        return questions;
    }
}
