package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.QuestionDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.UserRepository;
import com.dawan.gquizz.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private NewQuizzService newQuizzService;

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
        // Copie de la liste de question
        Set<QuestionDTO> questionsWithoutAnswer = new HashSet<>(questions);

        // Suppression de la valeur du champ answer pour chaque question dans la liste
        for (QuestionDTO q : questionsWithoutAnswer) {
            q.setAnswer("");
            q.setBadAnswers(new ArrayList<>(4));
        }
        // Set new id questions to user's lastQuizz
        List<String> idQuestions = new ArrayList<>();
        questions.forEach(questionDTO -> {
            idQuestions.add(questionDTO.get_id());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(questionDTO.get_id());
            System.out.println(questionDTO.getAnswer());
            System.out.println(questionDTO.getCategory());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        });

        Optional<User> optUser = userRepository.findById(userId);
        optUser.map(user -> {
            lastQuizzRepository.findByUserId(user.getId()).map(lastQuizz -> {
                lastQuizzRepository.save(lastQuizz.setCategory(categoryRepository.findByLabel(categoryLabel)).setUser(user).setIdQuestions(idQuestions));
                return lastQuizz;
            }).orElseGet(() -> lastQuizzRepository.save(new LastQuizz().setCategory(categoryRepository.findByLabel(categoryLabel)).setUser(user).setIdQuestions(idQuestions).setCurrentCount(0)));
            newQuizzService.resetCurrentScore(user);
            return user;
        }).orElseThrow();

        return questionsWithoutAnswer;
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