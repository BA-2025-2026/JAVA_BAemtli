package net.ictcampus.baemtli.monthassignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMonthAssignmentDTO {
    @NotNull(message = "Team ID is mandatory")
    private Integer teamId;

    @NotNull(message = "Chore Category ID is mandatory")
    private Integer choreCategoryId;

    @NotNull(message = "Month ID is mandatory")
    private Integer monthId;
}
