package com.project.webxaydung.Dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    @NotEmpty(message = "Contact's name cannot be empty")
    private String name;
    private String address;
    @NotEmpty(message = "Contact's phone cannot be empty")
    private String phone;
    private String email;
    @NotEmpty(message = "Contact's message cannot be empty")
    private String message;
}
