package com.project.webxaydung.Controllers;

import com.project.webxaydung.Dto.CandidateDTO;
import com.project.webxaydung.Models.Candidate;
import com.project.webxaydung.Repositories.CandidateRepository;
import com.project.webxaydung.Services.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/candidate")
@RequiredArgsConstructor
@Validated
public class CandidateController {
    private  final CandidateService candidateService;
    private  final CandidateRepository candidateRepository;

    @PostMapping("")
    public ResponseEntity<?> createCadidate(@Valid @RequestBody CandidateDTO candidateDTO, BindingResult result){
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try{
            candidateService.insertCandidate(candidateDTO);
            return ResponseEntity.ok("Insert candidate Successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/upload/")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file){
        if (file != null) {
            try {
                // Đọc tệp từ MultipartFile và xử lý nó ở đây
                byte[] fileBytes = file.getBytes();
                String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);
                return ResponseEntity.ok(base64Encoded);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("")// http://localhost:8080/api/v1/candidate
    public ResponseEntity<List<Candidate>> getAllCandidate(){
        List<Candidate> candidates=candidateService.getAllCandidate();
        return  ResponseEntity.ok(candidates);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCandidateById(
            @PathVariable("id") int id
    ) {
        candidateService.getCandidateById(id);
        return ResponseEntity.ok( candidateService.getCandidateById(id));
    }
    @GetMapping("/byJobOpening/{jobOpeningId}")
    public ResponseEntity<List<Candidate>> getCandidatesByJobOpeningId(@PathVariable Integer jobOpeningId) {
        List<Candidate> candidates = candidateService.getCandidatesByJobOpeningId(jobOpeningId);
        return ResponseEntity.ok(candidates);
    }
    @GetMapping("/{candidateId}/cv")
    public ResponseEntity<byte[]> getCvFile(@PathVariable int candidateId) {
        try {
            byte[] cvFileBytes = candidateService.getCvFile(candidateId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Set the appropriate content type for PDF
            headers.setContentDispositionFormData("filename", "cv_file.pdf"); // Set the file name

            return new ResponseEntity<>(cvFileBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable int id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.ok("Delete category with id: "+id+" successfully");
    }
}
