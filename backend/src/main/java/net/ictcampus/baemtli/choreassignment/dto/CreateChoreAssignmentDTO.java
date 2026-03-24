package net.ictcampus.baemtli.choreassignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateChoreAssignmentDTO {
    @NotNull(message = "Workday date is mandatory")
    private LocalDate workday;

    @NotNull(message = "Month Assignment ID is mandatory")
    private Integer monthAssignmentId;

    @NotNull(message = "Trainee ID is mandatory")
    private Integer traineeId;
}
