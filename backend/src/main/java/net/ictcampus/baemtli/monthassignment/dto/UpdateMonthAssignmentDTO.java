package net.ictcampus.baemtli.monthassignment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateMonthAssignmentDTO {
    private Integer teamId;
    private Integer choreCategoryId;

    @Min(value = 1, message = "Month Integer needs to be at least 1.")
    @Max(value = 12, message = "Month Integer cannot be bigger than 12.")
    private Integer monthInt;
}
