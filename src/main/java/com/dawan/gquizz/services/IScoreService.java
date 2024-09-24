package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Score;

import java.util.List;

public interface IScoreService {
    List<Score> findByUserEmail(String email);
}
