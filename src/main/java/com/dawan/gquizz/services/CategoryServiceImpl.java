package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Category;
import com.dawan.gquizz.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    // Injection de dépendance du repository CategoryRepository
    @Autowired
    private CategoryRepository categoryRepository;

    // Implémentation de la méthode pour récupérer toutes les catégories
    @Override
    public List<Category> findAllCategories() {
        // Appel du repository pour récupérer toutes les catégories
        return categoryRepository.findAll();
    }
}

