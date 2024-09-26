package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;

public interface ILastQuizzService {


	LastQuizz update(Long lastQuizzId, LastQuizz lastQuizz) throws Exception;

}