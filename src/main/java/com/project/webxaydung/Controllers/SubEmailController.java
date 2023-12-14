package com.project.webxaydung.Controllers;

import com.project.webxaydung.Dto.SubEmailDTO;
import com.project.webxaydung.Models.SubEmail;
import com.project.webxaydung.Services.SubEmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/subemail")
@RequiredArgsConstructor
@Validated
public class SubEmailController {
    private final SubEmailService subEmailService;
    @GetMapping("")// http://localhost:8080/api/v1/subemail
    public ResponseEntity<List<SubEmail>> getAllEmail(){

        List<SubEmail> subEmails=subEmailService.getAllSubemail();
        return  ResponseEntity.ok(subEmails);
    }

    @PostMapping("")
    public ResponseEntity<?> createSubEmail(@Valid @RequestBody SubEmailDTO subEmailDTO, BindingResult result){
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        subEmailService.createSubEmail(subEmailDTO);
        return ResponseEntity.ok("Insert SubEmail Successfully");
    }

}
