package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements IScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public void updateBestScore(User user) {
        Category cat = user.getLastQuizz().getCategory();

        scoreRepository.save(scoreRepository.findOneByUser_IdAndCategory_Id(user.getId(), cat.getId()).map(score -> {
            if (user.getLastQuizz().getCurrentScore() > score.getBestScore())
                score.setBestScore(user.getLastQuizz().getCurrentScore());
            return score;
        }).orElseGet(() -> new Score(user, cat, user.getLastQuizz().getCurrentScore())));
    }
}