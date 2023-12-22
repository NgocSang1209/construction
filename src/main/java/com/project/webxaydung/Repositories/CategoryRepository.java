package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.Category;
import com.project.webxaydung.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<User> findByCode(String code);
}
