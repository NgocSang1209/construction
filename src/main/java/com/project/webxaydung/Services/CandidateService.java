package com.project.webxaydung.Services;
import com.project.webxaydung.Models.JobOpening;
import com.project.webxaydung.Repositories.JopOpeningRepository;
import com.project.webxaydung.Dto.CandidateDTO;
import com.project.webxaydung.Models.Candidate;
import com.project.webxaydung.Models.Category;
import com.project.webxaydung.Repositories.CandidateRepository;
import com.project.webxaydung.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private  final CandidateRepository candidateRepository;
    private  final JopOpeningRepository jopOpeningRepository;
    public Candidate insertCandidate(CandidateDTO candidateDTO) throws DataNotFoundException {
        JobOpening existingJob = jopOpeningRepository
                .findById(candidateDTO.getJob_opening_id())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find Job with id: "+candidateDTO.getJob_opening_id()));
        if (!isValidEmail(candidateDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email không hợp lệ.");
        }
        if (!isValidPhoneNumber(candidateDTO.getPhone())) {
            throw new DataIntegrityViolationException("Số điện thoại không hợp lệ.");
        }
        //byte[] cvFileBytes= Base64.getDecoder().decode(candidateDTO.getCv_file());

        Candidate newCandidate= Candidate
                .builder()
                .name(candidateDTO.getName())
                .address(candidateDTO.getAddress())
                .phone(candidateDTO.getPhone())
                .email(candidateDTO.getEmail())
                .cv_file(candidateDTO.getCv_file())
                .jobOpening(existingJob)
                .build();
        return candidateRepository.save(newCandidate);
    }
    public List<Candidate> getAllCandidate(){
        return candidateRepository.findAll();
    }
    public Candidate getCandidateById(int id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("candidate not found"));
    }
    public List<Candidate> getCandidatesByJobOpeningId(Integer jobOpeningId) {
        return candidateRepository.findByJobOpeningId(jobOpeningId);
    }
//    public byte[] getCvFile(int candidateId) throws DataNotFoundException {
//        Candidate candidate = candidateRepository.findById(candidateId)
//                .orElseThrow(() -> new DataNotFoundException("Candidate not found"));
//
//        return candidate.getCv_file();
//    }

    public void deleteCandidate(int  id) {
        candidateRepository.deleteById(id);
    }

    //xác thực email
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches(".*@.*\\..*");
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "\\d{10}";
        return phoneNumber.matches(regex);
    }

}
