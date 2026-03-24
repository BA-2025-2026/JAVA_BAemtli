package net.ictcampus.baemtli.choreassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoreAssignmentDTO {
    private Integer id;
    private LocalDate workday;
    private Integer monthAssignmentId;
    private Integer traineeId;
    private String traineeName; // Komfort-Feld für das Frontend
}
