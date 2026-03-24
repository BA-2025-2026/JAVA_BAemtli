package net.ictcampus.baemtli.trainee.dto;

import net.ictcampus.baemtli.trainee.Trainee;

public class TraineeMapper {

    public static TraineeDTO toDto(Trainee trainee) {
        return new TraineeDTO(
                trainee.getId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getTeam() != null ? trainee.getTeam().getId() : null
        );
    }

    public static void updateFromDto(Trainee trainee, UpdateTraineeDTO dto) {
        // Always check null first, else .isBlank would give NPE if variable is null
        if (dto.getFirstName() != null && !dto.getFirstName().isBlank()) {
            trainee.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null && !dto.getLastName().isBlank()) {
            trainee.setLastName(dto.getLastName());
        }
    }
}
