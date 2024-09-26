package com.dawan.gquizz.utils;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

public class ScoreHelper {
    private ScoreHelper() {
    }

    public static Score createNewScore(User user, Category cat) {
        Score newScore = new Score().setUser(user).setCategory(cat).setBestScore(user.getCurrentScore());
        System.out.println();
        user.getScores().add(newScore);
        return newScore;
    }
}
