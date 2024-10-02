package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    long count();

    Category findByLabel(String label);
}
