package com.project.webxaydung.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobOpeningDTO {
    @NotEmpty(message = "JobOpening's title cannot be empty")
    private String title;

    private String description;
    @NotEmpty(message = "JobOpening's requirement cannot be empty")
    private String requirement;
    private Date start_day;
    private Date end_day;
    private Integer hiring_needs;
}
