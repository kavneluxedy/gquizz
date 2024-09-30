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

    @Autowired
    private CategoryServiceImpl categoryService;

    public List<Category> findAllCategory() {
        return categoryService.findAllCategories();
    }
}
