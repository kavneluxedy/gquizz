package com.dawan.gquizz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;

@Service
public class LastQuizzServiceImpl implements ILastQuizzService {
	// Injection du dépôt des derniers quiz pour interagir avec la base de données
	@Autowired
	private LastQuizzRepository lastQuizzRepository;

	// Méthode pour mettre à jour un LastQuizz en fonction de son ID
	@Override
	public LastQuizz update(Long lastQuizzId, LastQuizz lastQuizz) throws Exception {
	    // Vérifie si un LastQuizz avec l'ID spécifié existe dans la base de données
	    if (lastQuizzRepository.findById(lastQuizzId) != null) {
	        // Si le LastQuizz existe, le sauvegarder et le flush pour persister les modifications
	        return lastQuizzRepository.saveAndFlush(lastQuizz);
	    } else {
	        // Si le LastQuizz n'existe pas, en créer un nouveau
	        return lastQuizzRepository.save(lastQuizz);
	    }
	}

}
