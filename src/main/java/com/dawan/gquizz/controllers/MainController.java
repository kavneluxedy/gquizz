package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.UserRepository;
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
    private LastQuizzServiceImpl lastQuizzService;

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

    @GetMapping(path = "/quiz/{userId}/{categoryLabel}", produces = "application/json")
    public Set<QuestionDTO> getQuizByCategory(@PathVariable Long userId, @PathVariable String categoryLabel) throws Exception {
        Set<QuestionDTO> questions = questionService.getQuizByCategory(categoryLabel);

        // Set new id questions to lastQuizz
        List<String> idQuestions = new ArrayList<>();
        questions.forEach(questionDTO -> idQuestions.add(questionDTO.get_id()));

        Optional<User> optUser = userRepository.findById(userId);
        User user = null;

        if (optUser.isPresent()) {
            user = optUser.get();
            LastQuizz lq = lastQuizzRepository.findByUserId(user.getId());

            if (lq == null) {
                lastQuizzRepository.save(new LastQuizz().setCategory(categoryRepository.findByLabel(categoryLabel)).setUser(user).setIdQuestions(idQuestions));
            } else {
                lastQuizzRepository.saveAndFlush(lq.setCategory(categoryRepository.findByLabel(categoryLabel)).setUser(user).setIdQuestions(idQuestions));
            }
        }
        return questions;
    }
}