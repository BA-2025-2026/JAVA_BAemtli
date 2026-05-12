package net.ictcampus.baemtli.choreassignment.dto;

import net.ictcampus.baemtli.choreassignment.ChoreAssignment;
import net.ictcampus.baemtli.trainee.Trainee;

import java.util.Optional;

public class ChoreAssignmentMapper {

    public static ChoreAssignmentDTO toDto(ChoreAssignment assignment) {
        String traineeName = Optional.ofNullable(assignment.getTrainee())
                .map(t -> t.getFirstName() + " " + t.getLastName())
                .orElse("Unassigned");

        String traineeNameOldMethod = assignment.getTrainee() != null
            ? assignment.getTrainee().getFirstName() + " " + assignment.getTrainee().getLastName() 
            : "Unassigned";

        Integer traineeId = Optional.ofNullable(assignment.getTrainee())
                .map(Trainee::getId)
                .orElse(null);

        return new ChoreAssignmentDTO(
                assignment.getId(),
                assignment.getWorkday().getDate(),
                assignment.getMonthAssignment().getId(),
                traineeId,
                traineeName
        );
    }
}
