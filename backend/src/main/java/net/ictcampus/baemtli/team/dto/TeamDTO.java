package net.ictcampus.baemtli.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ictcampus.baemtli.trainee.Trainee;
import net.ictcampus.baemtli.trainee.dto.TraineeSummaryDTO;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Integer id;
    private String name;
    private List<TraineeSummaryDTO> trainees;
}
