package net.ictcampus.baemtli.team.dto;

import net.ictcampus.baemtli.team.Team;
import net.ictcampus.baemtli.trainee.dto.TraineeDTO;
import net.ictcampus.baemtli.trainee.dto.TraineeSummaryDTO;

public class TeamMapper {

    public static TeamDTO toDto(Team team) {
        return new TeamDTO(
                team.getId(),
                team.getName(),
                team.getTrainees().stream()
                        .map(trainee -> new TraineeSummaryDTO(trainee.getId(), trainee.getFirstName(), trainee.getLastName()))
                        .toList()
        );
    }

    public static void updateFromDto(Team team, UpdateTeamDTO dto) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            team.setName(dto.getName());
        }
    }
}
