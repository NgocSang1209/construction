package com.project.webxaydung.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    @NotEmpty(message = "Contact's name cannot be empty")
    private String name;
    private String position;
    @NotEmpty(message = "Contact's phone cannot be empty")
    private String phone;
    @NotEmpty(message = "Contact's email cannot be empty")
    private String email;
}
