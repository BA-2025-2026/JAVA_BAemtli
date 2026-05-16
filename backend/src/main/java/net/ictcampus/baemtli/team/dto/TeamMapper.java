package net.ictcampus.baemtli.team.dto;

import net.ictcampus.baemtli.team.Team;
import net.ictcampus.baemtli.trainee.dto.TraineeSummaryDTO;
import net.ictcampus.baemtli.team.dto.TeamDTO;
import net.ictcampus.baemtli.team.dto.UpdateTeamDTO;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TeamMapper {

    public static TeamDTO toDto(Team team) {
        // Check if team has trainees
        List<TraineeSummaryDTO> traineeSummaryDTOList = Optional.ofNullable(team.getTrainees())
                .map(list -> list.stream()
                        .map(t -> new TraineeSummaryDTO(t.getId(), t.getFirstName(), t.getLastName()))
                        .sorted(Comparator.comparing(TraineeSummaryDTO::getFirstName, String.CASE_INSENSITIVE_ORDER))
                        .toList())
                .orElse(List.of());

        return new TeamDTO(
                team.getId(),
                team.getName(),
                traineeSummaryDTOList
        );
    }

    public static void updateFromDto(Team team, UpdateTeamDTO dto) {
        Optional.ofNullable(dto.getName())
                .filter(n -> !n.isBlank())
                .ifPresent(team::setName);
    }
}
