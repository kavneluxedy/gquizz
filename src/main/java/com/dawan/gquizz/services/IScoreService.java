package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.ScoreDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;

import java.util.List;

public interface IScoreService {
    Score updateBestScore(User user);

    List<ScoreDTO> getTopScoreByCategory(Category categoryId);
}
