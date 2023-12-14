package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.SubEmail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubEmailRepository extends JpaRepository<SubEmail, Integer> {
    boolean existsByEmail(String email);
}
