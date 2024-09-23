package com.dawan.gquizz.utils;

import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;

public class ScoreHelper {
    public static Score createNewScore(User user, String category) {
        Score newScore = new Score().setUser(user).setCategory(category).setBestScore(user.getCurrentScore());
        user.getScores().add(newScore);
        return newScore;
    }
}
