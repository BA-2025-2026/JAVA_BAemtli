package net.ictcampus.baemtli.team.dto;

import net.ictcampus.baemtli.team.Team;

public class TeamMapper {

    public static TeamDTO toDto(Team team) {
        return new TeamDTO(
                team.getId(),
                team.getName()
        );
    }

    public static void updateFromDto(Team team, UpdateTeamDTO dto) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            team.setName(dto.getName());
        }
    }
}
