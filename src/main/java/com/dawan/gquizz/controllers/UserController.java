package com.dawan.gquizz.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawan.gquizz.entities.User;
import com.dawan.gquizz.services.UserServiceImpl;


@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
	
    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/create-account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAccount(@RequestBody User user) {
        try {
            // Appeler le service pour créer un utilisateur
            User newUser = userService.create(user);

            // Retourner une réponse succès avec l'utilisateur créé
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            // Gérer les erreurs, par exemple si l'email est déjà utilisé
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
