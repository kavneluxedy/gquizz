package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.User;

import java.util.List;
import java.util.Optional;

//Interface définissant les services liés aux utilisateurs
public interface IUserService {

 // Méthode pour récupérer un utilisateur par son identifiant
 User getById(Long userId) throws Exception;

 // Méthode pour créer un nouvel utilisateur
 void create(User user) throws Exception;

 // Méthode pour supprimer un utilisateur par son identifiant
 void deleteById(Long userId) throws Exception;

 // Méthode pour mettre à jour les informations d'un utilisateur existant
 void update(Long userId, User user) throws Exception;
}

