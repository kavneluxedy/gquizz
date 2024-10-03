package com.dawan.gquizz.controllers;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

	// Injection de la dépendance CategoryServiceImpl qui contient les méthodes pour gérer les catégories
	@Autowired
	private CategoryServiceImpl categoryService;

	// Méthode pour récupérer toutes les catégories
	// Elle appelle la méthode findAllCategories() du service injecté
	public List<Category> findAllCategory() {
	    return categoryService.findAllCategories(); // Renvoie la liste de toutes les catégories
	}

}
