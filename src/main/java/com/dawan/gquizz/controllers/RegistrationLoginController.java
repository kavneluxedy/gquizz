package com.dawan.gquizz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrationLoginController {

	// Déclaration des dépendances injectées via constructeur ou autre méthode
	private final UserRepository userRepository; // Repository pour gérer les utilisateurs en base de données
	private final PasswordEncoder passwordEncoder; // Encodeur de mot de passe (ex: BCrypt) pour sécuriser les mots de passe
	private final AuthenticationManager authenticationManager; // Gestionnaire d'authentification pour valider les identifiants

	// Endpoint POST pour l'inscription d'un utilisateur
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
	    // Vérification si l'email existe déjà dans la base de données
	    if (userRepository.findByEmail(user.getEmail()) != null) {
	        // Si l'email est déjà utilisé, retourne une réponse d'erreur avec un statut HTTP 400 (Bad Request)
	        return ResponseEntity.badRequest().body("L'email existe déjà.");
	    }
	    
	    // Si l'email n'existe pas, le mot de passe de l'utilisateur est encodé pour la sécurité
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    
	    // Sauvegarde de l'utilisateur dans la base de données et retour d'une réponse HTTP 200 (OK)
	    return ResponseEntity.ok(userRepository.save(user));
	}

	// Endpoint POST pour la connexion d'un utilisateur
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) {
	    try {
	        // Authentification de l'utilisateur avec l'email et le mot de passe fournis
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
	        
	        // Si l'authentification réussit, retourne une réponse HTTP 200 (OK)
	        return ResponseEntity.ok("Authentification validée");
	    } catch (Exception e) {
	        // En cas d'échec de l'authentification (ex: mauvais email ou mot de passe), retourne une réponse HTTP 401 (Unauthorized)
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe invalide");
	    }
	}

}
