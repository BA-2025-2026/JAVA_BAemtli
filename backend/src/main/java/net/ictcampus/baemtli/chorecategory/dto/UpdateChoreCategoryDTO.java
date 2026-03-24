package net.ictcampus.baemtli.chorecategory.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateChoreCategoryDTO {
    @Size(max = 30, message = "Name must be at most 30 characters")
    private String name;

    private String description;
}
