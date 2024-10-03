package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.ScoreDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;

import java.util.List;

//Interface définissant les services liés aux scores
public interface IScoreService {

 // Méthode pour mettre à jour le meilleur score d'un utilisateur
 Score updateBestScore(User user);

 // Méthode pour obtenir la liste des meilleurs scores pour une catégorie spécifiée
 List<ScoreDTO> getTopScoreByCategory(Category categoryId);
}

