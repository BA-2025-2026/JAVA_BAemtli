package net.ictcampus.baemtli.monthassignment.dto;

import net.ictcampus.baemtli.monthassignment.MonthAssignment;

public class MonthAssignmentMapper {

    public static MonthAssignmentDTO toDto(MonthAssignment assignment) {
        return new MonthAssignmentDTO(
                assignment.getId(),
                assignment.getTeam().getId(),
                assignment.getChoreCategory().getId(),
                assignment.getMonth().getId()
        );
    }
}
