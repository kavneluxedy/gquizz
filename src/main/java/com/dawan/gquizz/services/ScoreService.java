package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService implements IScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public Score findByEmailAndCategory(String email, String category) {
        return scoreRepository.findByUserEmailAndCategory(email, category);
    }
}
