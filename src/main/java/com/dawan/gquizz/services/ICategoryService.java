package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Category;

import java.util.List;

//Interface définissant les services liés aux catégories
public interface ICategoryService {

 // Méthode pour récupérer toutes les catégories
 List<Category> findAllCategories();
}

