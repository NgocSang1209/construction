package com.project.webxaydung.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "Category's name cannot be empty")
    private String name  ;
    @NotEmpty(message = "Category's code cannot be empty")
    private  String code ;
}
