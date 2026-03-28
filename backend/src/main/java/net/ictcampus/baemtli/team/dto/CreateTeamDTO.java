package net.ictcampus.baemtli.team.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamDTO {
    @NotBlank(message = "Name is required")
    @Size(min= 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;
}
