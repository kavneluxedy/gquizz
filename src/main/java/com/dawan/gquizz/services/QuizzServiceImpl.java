package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.repositories.LastQuizzRepository;

@Service
public class QuizzServiceImpl {

	@Autowired
	private LastQuizzRepository lastQuizzRepository; // Injection du repository pour accéder aux données LastQuizz

	// Récupère un LastQuizz par son identifiant
	public LastQuizz getLastQuizzById(Long id) {
	    // Utilise le repository pour chercher un LastQuizz par ID et retourne le résultat ou null si non trouvé
	    return lastQuizzRepository.findById(id).orElse(null);
	}

	// Sauvegarde un LastQuizz dans la base de données
	public void saveLastQuizz(LastQuizz lastQuizz) {
	    // Utilise le repository pour sauvegarder le LastQuizz
	    lastQuizzRepository.save(lastQuizz);
	}
}