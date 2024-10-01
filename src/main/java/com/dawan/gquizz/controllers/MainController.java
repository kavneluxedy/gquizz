package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.CategoryServiceImpl;
import com.dawan.gquizz.services.LastQuizzServiceImpl;
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
    private UserRepository userRepository;

    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private LastQuizzServiceImpl lastQuizzService;

    @GetMapping(path = "/getAllCategories")
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping(path = "/quiz/{categoryLabel}", produces = "application/json")
    public Set<QuestionDTO> getQuizByCategory(@PathVariable String categoryLabel) throws Exception {
        return questionService.getQuizByCategory(categoryLabel);
    }

    @GetMapping(path = "/quiz/{userId}/{categoryLabel}", produces = "application/json")
    public Set<QuestionDTO> getQuizByCategory(@PathVariable Long userId, @PathVariable String categoryLabel) throws Exception {
        Set<QuestionDTO> questions = questionService.getQuizByCategory(categoryLabel);

        // Set new id questions to user's lastQuizz
        List<String> idQuestions = new ArrayList<>();
        questions.forEach(questionDTO -> {
            System.out.println(questionDTO.get_id());
            idQuestions.add(questionDTO.get_id());
        });

        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = optUser.get();

            lastQuizzRepository.findByUserId(user.getId()).map(lastQuizz -> {
                lastQuizzRepository.save(lastQuizz.setCategory(categoryRepository.findByLabel(categoryLabel)).setUser(user).setIdQuestions(idQuestions));
                return lastQuizz;
            }).orElseGet(() -> lastQuizzRepository.save(new LastQuizz().setCategory(categoryRepository.findByLabel(categoryLabel)).setUser(user).setIdQuestions(idQuestions)));
        } else {
            throw new RuntimeException("Vous n'avez pas de compte ! Créez en un pour exposer votre score au monde entier");
        }

        return questions;
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
}