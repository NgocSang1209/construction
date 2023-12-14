package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.Contact;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
    List<Contact> findBySubDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
