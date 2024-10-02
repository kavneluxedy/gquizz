package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.repositories.LastQuizzRepository;

@Service
public class QuizzServiceImpl {

    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    public LastQuizz getLastQuizzById(Long id) {
        return lastQuizzRepository.findById(id).orElse(null);
    }

    public void saveLastQuizz(LastQuizz lastQuizz) {
        lastQuizzRepository.save(lastQuizz);
    }
}