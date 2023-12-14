package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
