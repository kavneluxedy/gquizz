package com.dawan.gquizz.utils;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.entities.User;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

public class ScoreHelper {
	// Classe utilitaire pour aider à la création d'objets Score
	private ScoreHelper() {
	    // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
	}

	public static Score createNewScore(User user, Category cat) {
	    // Crée une nouvelle instance de Score
	    Score newScore = new Score()
	        .setUser(user) // Définit l'utilisateur pour le score
	        .setCategory(cat) // Définit la catégorie pour le score
	        .setBestScore(user.getLastQuizz().getCurrentScore()); // Définit le meilleur score à partir du score actuel du quiz

	    // Affiche une ligne vide dans la console (peut-être à des fins de débogage)
	    System.out.println();

	    // Ajoute le nouveau score à la liste des scores de l'utilisateur
	    user.getScores().add(newScore);

	    // Retourne l'objet Score nouvellement créé
	    return newScore;
	}

}
