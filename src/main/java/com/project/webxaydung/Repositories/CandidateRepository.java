package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate,Integer> {

}
