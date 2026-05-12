package net.ictcampus.baemtli.trainee.dto;

import net.ictcampus.baemtli.team.Team;
import net.ictcampus.baemtli.trainee.Trainee;

import java.util.Optional;

public class TraineeMapper {

    public static TraineeDTO toDto(Trainee trainee) {

        Integer teamId = Optional.ofNullable(trainee.getTeam())
                .map(Team::getId)
                .orElse(null);

        return new TraineeDTO(
                trainee.getId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                teamId
        );
    }

    public static void updateFromDto(Trainee trainee, UpdateTraineeDTO dto) {
        Optional.ofNullable(dto.getFirstName())
                .filter(n -> !n.isBlank())
                .ifPresent(trainee::setFirstName);

        Optional.ofNullable(dto.getLastName())
                .filter(n -> !n.isBlank())
                .ifPresent(trainee::setLastName);
    }
}
