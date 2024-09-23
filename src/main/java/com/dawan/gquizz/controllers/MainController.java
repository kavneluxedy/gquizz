package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.services.QuestionServiceImpl;
import com.dawan.gquizz.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private QuestionServiceImpl questionService;

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

    @GetMapping(path = "/quiz", produces = "application/json")
    public Set<QuestionDTO> getQuiz() throws Exception {
        return questionService.getQuiz();
    }

    @GetMapping(path = "/quiz/{category}", produces = "application/json")
    public Set<QuestionDTO> getQuizByCategory(@PathVariable("category") String category) throws Exception {
        return questionService.getQuizByCategory(category);
    }
}
