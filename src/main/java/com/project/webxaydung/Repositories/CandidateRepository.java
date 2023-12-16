package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate,Integer> {
    List<Candidate> findByJobOpeningId(Integer jobOpeningId);
}
