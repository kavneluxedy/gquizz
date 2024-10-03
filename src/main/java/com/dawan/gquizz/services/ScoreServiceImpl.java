package com.dawan.gquizz.services;

import com.dawan.gquizz.dtos.ScoreDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.ScoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//Classe d'implémentation du service de gestion des scores
public class ScoreServiceImpl implements IScoreService {

 // Injection du dépôt des scores pour interagir avec la base de données
 @Autowired
 private ScoreRepository scoreRepository;

 // Injection du service de quiz pour réinitialiser les scores
 @Autowired
 private NewQuizzService newQuizzService;

 // Méthode pour mettre à jour le meilleur score d'un utilisateur
 @Override
 public Score updateBestScore(User user) {
     // Récupération de la catégorie du dernier quiz de l'utilisateur
     Category cat = user.getLastQuizz().getCategory();
     
     // Recherche du score de l'utilisateur dans la base de données pour cette catégorie
     return scoreRepository.save(scoreRepository.findOneByUser_IdAndCategory_Id(user.getId(), cat.getId()).map(score -> {
         // Si le score actuel est 0, ne rien faire
         if (user.getLastQuizz().getCurrentScore() == 0) return null;
         // Si le score actuel est supérieur au meilleur score enregistré, mettre à jour le meilleur score
         if (user.getLastQuizz().getCurrentScore() > score.getBestScore()) {
             return score.setBestScore(user.getLastQuizz().getCurrentScore());
         }
         // Réinitialiser le score courant si le meilleur score n'a pas changé
         newQuizzService.resetCurrentScore(user);
         return score;
     }).orElseGet(() -> 
         // Si aucun score n'est trouvé, créer un nouveau score pour l'utilisateur
         new Score(user, cat, user.getLastQuizz().getCurrentScore()))
     );
 }

 // Méthode pour récupérer les meilleurs scores par catégorie
 @Override
 public List<ScoreDTO> getTopScoreByCategory(Category category) {
     // Recherche des scores dans la base de données pour la catégorie spécifiée, triés par meilleur score
     return scoreRepository.findByCategoryOrderByBestScore(category).map(s -> {
         ModelMapper mapper = new ModelMapper();
         // Mapper les entités Score en ScoreDTO pour la réponse
         List<ScoreDTO> scores = s.stream()
             .map(scoreStream -> mapper.map(scoreStream, ScoreDTO.class))
             .toList();
         // Afficher les scores dans la console
         System.out.println(scores);
         return scores;
     }).orElseGet(() -> {
         // Si aucun score n'est trouvé, afficher un message d'erreur dans la console
         System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
         return null; // Retourner null si aucune donnée n'est trouvée
     });
 }
}
