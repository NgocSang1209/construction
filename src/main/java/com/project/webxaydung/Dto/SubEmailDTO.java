package com.project.webxaydung.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubEmailDTO {
    @NotEmpty(message = "Email cannot be empty")
    private String email ;
}
