package net.ictcampus.baemtli.monthassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthAssignmentDTO {
    private Integer id;
    private Integer teamId;
    private Integer choreCategoryId;
    private Integer monthId;
}
