package com.project.webxaydung.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
        @NotBlank(message = " account is required")
        private String account;

        @NotBlank(message = "Password cannot be blank")
        private String password;

        @Min(value = 1, message = "You must enter role's Id")
        private Long role_id;
}
