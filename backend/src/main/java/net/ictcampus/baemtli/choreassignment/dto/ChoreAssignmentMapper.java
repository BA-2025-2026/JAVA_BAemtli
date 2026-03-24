package net.ictcampus.baemtli.choreassignment.dto;

import net.ictcampus.baemtli.choreassignment.ChoreAssignment;

public class ChoreAssignmentMapper {

    public static ChoreAssignmentDTO toDto(ChoreAssignment assignment) {
        String traineeName = assignment.getTrainee() != null 
            ? assignment.getTrainee().getFirstName() + " " + assignment.getTrainee().getLastName() 
            : "Unassigned";

        return new ChoreAssignmentDTO(
                assignment.getId(),
                assignment.getWorkday().getDate(),
                assignment.getMonthAssignment().getId(),
                assignment.getTrainee() != null ? assignment.getTrainee().getId() : null,
                traineeName
        );
    }
}
