package net.ictcampus.baemtli.monthassignment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMonthAssignmentDTO {
    @NotNull(message = "Team ID is mandatory")
    private Integer teamId;

    @NotNull(message = "Chore Category ID is mandatory")
    private Integer choreCategoryId;

    @NotNull(message = "Month Integer is mandatory")
    @Min(value = 1, message = "Month Integer needs to be at least 1.")
    @Max(value = 12, message = "Month Integer cannot be bigger than 12.")
    private Integer monthInt;
}
