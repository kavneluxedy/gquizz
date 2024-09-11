package com.dawan.gquizz.controllers;

import com.dawan.gquizz.services.QuestionServiceImpl;
import com.dawan.gquizz.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
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
    public @ResponseBody String generateRandomQuestion() {
        return questionService.generateRandomQuestion();
    }

    @GetMapping(path = "/cat", produces = "application/json")
    public @ResponseBody String generateRandomQuestionByCategory() {
        return questionService.generateRandomQuestionByCategory("sport");
    }
}
