package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    long count();

    List<Category> findAll();

    Category findByLabel(String label);
}
