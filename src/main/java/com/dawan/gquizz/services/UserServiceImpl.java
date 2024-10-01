package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    public UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Crypter le mot de passe
        userRepository.save(user);
    }

    @Override
    public List<String> findAllCategory() {
        return List.of();
    }
    
    @Override
    public User getById(Long userId) throws Exception {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new Exception("Utilisateur non trouvé"));
    }
    
    /*@Override
    public User getById(Long userId) throws Exception {
        Optional<User> u = userRepository.findById(userId);
        User user = null;
        if (u.isPresent()) {
            user = u.get();
        }
        return user;
    }*/

    @Override
    public User create(User user) throws Exception {
    	
    // Vérifier si l'email est déjà utilisé
    	boolean exists = userRepository.existsById(user.getId());
    	if (exists) {
    	User userFound = userRepository.findById(user.getId()).orElse(null);
    	if (userFound != null) {
    	 String email = userFound.getEmail();
    	 System.out.println("Email de l'utilisateur : " + email);
        } else {
            System.out.println("Utilisateur non trouvé.");
        }
    	} else {
    	    System.out.println("L'utilisateur n'existe pas, créer utilisateur");
    	} 
        // Créer un nouvel utilisateur
        User u = new User();
        u.setPseudo(user.getPseudo());
        u.setEmail(user.getEmail());
        
        // Attention : il est recommandé de chiffrer le mot de passe avant de le sauvegarder
        user.setPassword(u.getPassword()); // TODO: Crypter password ici

        // Sauvegarder l'utilisateur dans la base de données
        return userRepository.save(user);
    }

    @Override
    public void update(Long userId, User user) throws Exception {
        if (getById(userId) != null) {
            userRepository.save(user);
        } else {
            create(user);
        }
    }

    @Override
    public void deleteById(Long userId) throws Exception {
        userRepository.deleteById(userId);
    }
}
