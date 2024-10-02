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
    private ScoreServiceImpl scoreService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/top/{categoryLabel}")
    public List<ScoreDTO> getTopScoreByCategory(@PathVariable("categoryLabel") String categoryLabel) {
        // DO OPTIONAL
        Category cat = categoryRepository.findByLabel(categoryLabel);
        return scoreService.getTopScoreByCategory(cat);
    }
}
