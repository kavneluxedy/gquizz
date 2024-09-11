package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.Quiz;
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
    public @ResponseBody Quiz getRandomQuestion(@RequestParam(value = "count", required = false) Integer count) {
        return questionService.getRandomQuestion();
    }

    @GetMapping(path = "/cat", produces = "application/json")
    public @ResponseBody Quiz getRandomQuestionByCategory() {
        return questionService.getRandomQuestionByCategory("sport");
    }

    @GetMapping(path = "/quiz", produces = "application/json")
    public @ResponseBody Set<Quiz> getQuiz() {
        return questionService.getQuiz();
    }

    @GetMapping(path = "/quiz/{category}", produces = "application/json")
    public @ResponseBody Set<Quiz> getQuizByCategory(@PathVariable("category") String category) {
        return questionService.getQuizByCategory(category);
    }
}
