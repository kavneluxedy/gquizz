package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewQuizzService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    // Cette méthode est également appelée à la fin du quiz (10 questions)
    public void resetCurrentScore(User user) {
        lastQuizzRepository.saveAndFlush(lastQuizzRepository.findByUserId(user.getId()).map(lastQuizz -> {
                    lastQuizz.setCurrentScore(0);
                    lastQuizz.setCurrentCount(0);
                    return lastQuizz;
                })
                .orElseGet(() -> new LastQuizz()
                        .setCurrentScore(0)
                        .setCurrentCount(0)
                        .setCategory(user.getLastQuizz().getCategory())
                        .setUser(user)));
    }
}
