package com.project.webxaydung.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    @NotEmpty(message = "Candidate's name cannot be empty")
    private  String name ;
    @NotEmpty(message = "Candidate's email cannot be empty")
    private String email;
    @NotEmpty(message = "Candidate's phone cannot be empty")
    private  String phone;
    private  String address;
    @NotEmpty(message = "Candidate's cv cannot be empty")
    private  String cv_file;
    private int job_opening_id;
}
