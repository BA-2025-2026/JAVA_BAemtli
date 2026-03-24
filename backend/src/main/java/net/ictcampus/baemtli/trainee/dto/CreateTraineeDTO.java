package net.ictcampus.baemtli.trainee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTraineeDTO {
    @NotBlank(message = "First name is required")
    @Size(max = 30, message = "First name must be at most 30 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 30, message = "Last name must be at most 30 characters")
    private String lastName;

    @NotNull(message = "Team ID is required")
    private Integer teamId;
}
