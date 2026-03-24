package net.ictcampus.baemtli.trainee.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTraineeDTO {
    @Size(max = 30, message = "First name must be at most 30 characters")
    private String firstName;

    @Size(max = 30, message = "Last name must be at most 30 characters")
    private String lastName;

    private Integer teamId;
}
