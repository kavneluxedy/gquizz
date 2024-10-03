package com.dawan.gquizz.controllers;

import com.dawan.gquizz.dtos.ScoreDTO;
import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.entities.Score;
import com.dawan.gquizz.repositories.CategoryRepository;
import com.dawan.gquizz.services.ScoreServiceImpl;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@RequestMapping("/score")
public class ScoreController {

	@Autowired
	private ScoreServiceImpl scoreService; // Service qui gère la logique métier pour les scores

	@Autowired
	private CategoryRepository categoryRepository; // Repository pour gérer les catégories dans la base de données

	// Endpoint GET pour récupérer les meilleurs scores par catégorie
	@GetMapping("/top/{categoryLabel}")
	public List<ScoreDTO> getTopScoreByCategory(@PathVariable("categoryLabel") String categoryLabel) {
	    // Recherche de la catégorie à partir du label fourni dans l'URL
	    Category cat = categoryRepository.findByLabel(categoryLabel);
	    
	    // Retourne la liste des meilleurs scores pour cette catégorie en appelant le service des scores
	    return scoreService.getTopScoreByCategory(cat);
	}
}
