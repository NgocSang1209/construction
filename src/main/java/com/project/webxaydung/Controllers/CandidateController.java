package com.project.webxaydung.Controllers;

import com.project.webxaydung.Dto.CandidateDTO;
import com.project.webxaydung.Models.Candidate;
import com.project.webxaydung.Services.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/candidate")
@RequiredArgsConstructor
@Validated
public class CandidateController {
    private  final CandidateService candidateService;

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
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            try {
                // Đọc tệp từ MultipartFile và xử lý nó ở đây
                byte[] fileBytes = file.getBytes();
                String fileContents = new String(fileBytes);
                // Tiến hành lưu hoặc xử lý chuỗi `fileContents`
                return ResponseEntity.ok(fileContents);
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
        return ResponseEntity.ok("Candidate with ID: " + candidateService.getCandidateById(id));
    }
}
