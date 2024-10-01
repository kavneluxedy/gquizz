package com.dawan.gquizz.services;

import com.dawan.gquizz.entities.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAllCategories();
}
