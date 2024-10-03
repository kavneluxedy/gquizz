package com.dawan.gquizz.services;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    // Référence au repository utilisateur pour interagir avec la base de données
    private final UserRepository userRepository;

    // Méthode pour charger les détails de l'utilisateur par son nom d'utilisateur (email)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recherche d'un utilisateur dans la base de données par son email
        User user = userRepository.findByEmail(email);
        
        // Vérification si l'utilisateur existe
        if(user == null) {
            // Si l'utilisateur n'est pas trouvé, une exception est lancée
            throw new UsernameNotFoundException("Aucun utilisateur ne correspond à : " + email);
        }
        
        // Création et retour d'un objet UserDetails avec l'email, le mot de passe, et les rôles de l'utilisateur
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), // Email de l'utilisateur
            user.getPassword(), // Mot de passe de l'utilisateur (doit être crypté)
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole())) // Liste des rôles de l'utilisateur sous forme d'autorité
        );
    }
}

