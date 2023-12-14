package com.project.webxaydung.Services;
import com.project.webxaydung.Dto.CandidateDTO;
import com.project.webxaydung.Models.Candidate;
import com.project.webxaydung.Repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private  final CandidateRepository candidateRepository;

    public Candidate insertCandidate(CandidateDTO candidateDTO){
        if (!isValidEmail(candidateDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email không hợp lệ.");
        }
        if (!isValidPhoneNumber(candidateDTO.getPhone())) {
            throw new DataIntegrityViolationException("Số điện thoại không hợp lệ.");
        }
        Candidate newCandidate= Candidate
                .builder()
                .name(candidateDTO.getName())
                .address(candidateDTO.getAddress())
                .phone(candidateDTO.getPhone())
                .email(candidateDTO.getEmail())
                .cv_file(candidateDTO.getCv_file())
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

    public void deleteContact(int  id) {

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
