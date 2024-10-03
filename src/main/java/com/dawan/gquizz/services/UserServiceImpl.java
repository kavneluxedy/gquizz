package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	public UserRepository userRepository; // Injection du repository pour accéder aux données User

	@Override
	public User getById(Long userId) throws Exception {
	    // Recherche un utilisateur par son ID
	    Optional<User> u = userRepository.findById(userId);
	    User user = null; // Variable pour stocker l'utilisateur trouvé
	    if (u.isPresent()) { // Vérifie si l'utilisateur est présent
	        user = u.get(); // Récupère l'utilisateur
	    }
	    return user; // Retourne l'utilisateur ou null si non trouvé
	}

	@Override
	public void create(User user) throws Exception {
	    // Sauvegarde un nouvel utilisateur dans la base de données
	    userRepository.save(user);
	}

	@Override
	public void update(Long userId, User user) throws Exception {
	    // Vérifie si l'utilisateur à mettre à jour existe
	    if (getById(userId) != null) {
	        // Met à jour l'utilisateur existant
	        userRepository.saveAndFlush(user);
	    } else {
	        // Si l'utilisateur n'existe pas, crée un nouvel utilisateur
	        create(user);
	    }
	}

	@Override
	public void deleteById(Long userId) throws Exception {
	    // Supprime l'utilisateur par son ID
	    userRepository.deleteById(userId);
	}

}
