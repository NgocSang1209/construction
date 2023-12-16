package com.project.webxaydung.Services;

import com.project.webxaydung.Dto.SubEmailDTO;
import com.project.webxaydung.Models.SubEmail;
import com.project.webxaydung.Repositories.SubEmailRepository;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubEmailService {
    private  final SubEmailRepository subEmailRepository;
    public SubEmail createSubEmail(SubEmailDTO subEmailDTO){
        String email= subEmailDTO.getEmail();
        if(subEmailRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        SubEmail newSubEmail = SubEmail
                .builder()
                .email(subEmailDTO.getEmail())
                .build();
        return subEmailRepository.save(newSubEmail);
    }

    public List<SubEmail> getAllSubemail(){
        return subEmailRepository.findAll();
    }
    @Transactional
    public void deleteSubemail(int id) {
        subEmailRepository.deleteById(id);
    }
}
