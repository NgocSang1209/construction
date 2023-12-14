package com.project.webxaydung.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "account cannot be blank")
    private String account;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @NotNull(message = "Role ID is required")
    private  Long role_id;
    @JsonProperty("employees_id")@NotNull(message = "Employee Id is required")
    private int employeeId;
}

