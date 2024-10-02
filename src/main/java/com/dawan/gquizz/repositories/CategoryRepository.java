package com.dawan.gquizz.repositories;

import com.dawan.gquizz.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    long count();

    Category findByLabel(@Param("label") String label);
}
