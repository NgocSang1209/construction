package com.project.webxaydung.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewDTO {
    private  int category_id;
    @NotEmpty(message = "New's title cannot be empty")
    private String title;
    @NotEmpty(message = "New's description cannot be empty")
    private String short_description;
    @NotEmpty(message = "New's content cannot be empty")
    private String content;
    private String thumbnail;
}
