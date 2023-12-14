package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NewRepository extends JpaRepository<New,Integer> {

    @Query("SELECT n FROM New n WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR n.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR n.title LIKE %:keyword% OR n.short_description LIKE %:keyword%)")
    Page<New> searchNews(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword, Pageable pageable);
    Optional<New> getDetailNewById(int newId);
}
