package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Score;

public interface IScoreService {
    Score findByEmailAndCategory(String email, String category);
}
