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

    @GetMapping(path = "/quiz/{email}", produces = "application/json")
    public Set<QuestionDTO> getQuiz(@PathVariable("email") String email) throws Exception {
        lastQuizzRepository.findByUserEmail(email);
        return questionService.getQuiz();
    }

    @GetMapping(path = "/quiz/{email}/{category}", produces = "application/json")
    public Set<QuestionDTO> getQuizByCategory(@PathVariable("category") String category, @PathVariable("email") String email) throws Exception {
        Set<QuestionDTO> questions = questionService.getQuizByCategory(category);

        // Set new Id Questions to User
        List<String> idQuestions = new ArrayList<>();
        questions.forEach(questionDTO -> {
            System.out.println(questionDTO.get_id());
            idQuestions.add(questionDTO.get_id());
        });

        Optional<User> user = userService.getByEmail(email).stream().findFirst();
        LastQuizz lq = lastQuizzRepository.findByUserEmail(email);
        user.ifPresent(u -> lastQuizzRepository.saveAndFlush(lq.setCategory(category).setUser(u).setIdQuestions(idQuestions)));

        return questions;
    }
}
