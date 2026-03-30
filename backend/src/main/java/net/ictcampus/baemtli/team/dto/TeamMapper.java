package net.ictcampus.baemtli.team.dto;

import net.ictcampus.baemtli.team.Team;
import net.ictcampus.baemtli.trainee.dto.TraineeDTO;
import net.ictcampus.baemtli.trainee.dto.TraineeMapper;

import java.util.List;

public class TeamMapper {

    public static TeamDTO toDto(Team team) {
        List<TraineeDTO> traineeDtos = List.of();
        if (team.getTrainees() != null) {
            traineeDtos = team.getTrainees().stream()
                    .map(TraineeMapper::toDto)
                    .toList();
        }

        return new TeamDTO(
                team.getId(),
                team.getName(),
                traineeDtos
        );
    }

    public static void updateFromDto(Team team, UpdateTeamDTO dto) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            team.setName(dto.getName());
        }
    }
}
