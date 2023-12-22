package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface JopOpeningRepository extends JpaRepository<JobOpening,Integer> {
//    @Query("SELECT j FROM JobOpening j WHERE j.is_active = 1 AND j.vacancies > 0 AND j.end_day > :currentDate")
//    List<JobOpening> findAvailableJobOpenings(@Param("currentDate") Date currentDate);

}
