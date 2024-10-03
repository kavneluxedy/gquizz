package com.dawan.gquizz.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dawan.gquizz.services.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	// Injection de dépendance pour le service personnalisé de détails de l'utilisateur
	private final CustomUserDetailsService userDetailsService;

	// Bean pour encoder les mots de passe à l'aide de BCrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
	    // Utilisation de BCrypt pour le hachage des mots de passe
	    return new BCryptPasswordEncoder();
	}

	// Bean pour configurer CORS (Cross-Origin Resource Sharing)
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    
	    // Autorise toutes les origines
	    configuration.setAllowedOrigins(List.of("*"));
	    
	    // Définit les méthodes HTTP autorisées
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PATCH", "OPTIONS"));
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    
	    // Applique la configuration CORS à toutes les URL
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	// Bean pour configurer le filtre de sécurité
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf(AbstractHttpConfigurer::disable) // Désactive la protection CSRF
	            .authorizeHttpRequests(authorize -> 
	                // Autorise l'accès à l'URL de connexion
	                authorize.requestMatchers("/login").permitAll()
	                .anyRequest().permitAll() // Permet l'accès à toutes les autres requêtes
	            ).build(); // Construit la chaîne de filtres de sécurité
	}

	// Bean pour configurer le gestionnaire d'authentification
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
	    // Obtient l'instance de AuthenticationManagerBuilder
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    
	    // Configure le gestionnaire d'authentification avec le service utilisateur et l'encodeur de mot de passe
	    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

	    // Retourne le gestionnaire d'authentification construit
	    return authenticationManagerBuilder.build();
	}

}