package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;

//Interface définissant les services liés au dernier quiz
public interface ILastQuizzService {

 // Méthode pour mettre à jour un dernier quiz en fonction de son ID
 LastQuizz update(Long lastQuizzId, LastQuizz lastQuizz) throws Exception;
}
