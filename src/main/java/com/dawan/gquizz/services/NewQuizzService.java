package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.LastQuizz;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.LastQuizzRepository;
import com.dawan.gquizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewQuizzService {

    // Injection du dépôt des utilisateurs pour interagir avec la base de données
    @Autowired
    private UserRepository userRepository;

    // Injection du dépôt des derniers quiz pour interagir avec la base de données
    @Autowired
    private LastQuizzRepository lastQuizzRepository;

    // Cette méthode est appelée pour réinitialiser le score actuel d'un utilisateur
    // Cela se produit également à la fin du quiz, une fois que les 10 questions ont été répondues
    public LastQuizz resetCurrentScore(User user) {
        // Recherche le LastQuizz associé à l'utilisateur
        return lastQuizzRepository.save(lastQuizzRepository.findByUserId(user.getId()).map(lastQuizz -> {
                    // Si le LastQuizz existe, réinitialise le score et le compte
                    lastQuizz.setCurrentScore(0);
                    lastQuizz.setCurrentCount(0);
                    return lastQuizz; // Retourne le LastQuizz mis à jour
                })
                // Si aucun LastQuizz n'existe pour l'utilisateur, crée un nouveau LastQuizz
                .orElseGet(() -> lastQuizzRepository.save(lastQuizzRepository.save(new LastQuizz()
                        .setCurrentScore(0) // Initialise le score à 0
                        .setCurrentCount(0) // Initialise le compte à 0
                        .setCategory(user.getLastQuizz().getCategory()) // Définit la catégorie du dernier quiz
                        .setUser(user)))));
    }
}
