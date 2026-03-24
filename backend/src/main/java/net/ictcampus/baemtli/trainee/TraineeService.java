package net.ictcampus.baemtli.trainee;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.baemtli.security.AuthorizationService;
import net.ictcampus.baemtli.security.Permission;
import net.ictcampus.baemtli.team.TeamRepository;
import net.ictcampus.baemtli.trainee.dto.CreateTraineeDTO;
import net.ictcampus.baemtli.trainee.dto.TraineeDTO;
import net.ictcampus.baemtli.trainee.dto.TraineeMapper;
import net.ictcampus.baemtli.trainee.dto.UpdateTraineeDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final TeamRepository teamRepository;
    private final AuthorizationService authz;

    public TraineeService(TraineeRepository traineeRepository, TeamRepository teamRepository, AuthorizationService authz) {
        this.traineeRepository = traineeRepository;
        this.teamRepository = teamRepository;
        this.authz = authz;
    }

    public List<TraineeDTO> getTrainees(Authentication auth, Integer teamIdFilter) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Permission.TRAINEE_READ_ALL))) {
            if (teamIdFilter != null) {
                return getTraineesByTeam(teamIdFilter);
            }
            return getAllTrainees();
        } else {
            // User has Permission.TRAINEE_READ_TEAM
            Integer userTeamId = authz.getTeamId(auth);
            if (teamIdFilter != null && !teamIdFilter.equals(userTeamId)) {
                throw new AccessDeniedException("Not allowed to access trainees of another team");
            }
            return getTraineesByTeam(userTeamId);
        }
    }

    public List<TraineeDTO> getAllTrainees() {
        return traineeRepository.findAll().stream()
                .map(TraineeMapper::toDto)
                .toList();
    }

    public List<TraineeDTO> getTraineesByTeam(Integer teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new EntityNotFoundException("Team not found");
        }
        return traineeRepository.findByTeam_Id(teamId).stream()
                .map(TraineeMapper::toDto)
                .toList();
    }

    public TraineeDTO getTraineeById(Integer id) {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));
        return TraineeMapper.toDto(trainee);
    }

    public TraineeDTO createTrainee(CreateTraineeDTO dto) {
        if (traineeRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isPresent()) {
            throw new EntityExistsException("Trainee with this name already exists");
        }

        Trainee trainee = new Trainee()
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setTeam(teamRepository.findById(dto.getTeamId())
                        .orElseThrow(() -> new EntityNotFoundException("Team not found")));

        Trainee savedTrainee = traineeRepository.save(trainee);
        return TraineeMapper.toDto(savedTrainee);
    }

    public TraineeDTO updateTrainee(Integer id, UpdateTraineeDTO dto) {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));

        if (dto.getFirstName() != null || dto.getLastName() != null) {
            String newFirstName = dto.getFirstName() != null ? dto.getFirstName() : trainee.getFirstName();
            String newLastName = dto.getLastName() != null ? dto.getLastName() : trainee.getLastName();
            
            traineeRepository.findByFirstNameAndLastName(newFirstName, newLastName)
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(id)) {
                            throw new EntityExistsException("Trainee with this name already exists");
                        }
                    });
        }

        if (dto.getTeamId() != null) {
            trainee.setTeam(teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team not found")));
        }

        TraineeMapper.updateFromDto(trainee, dto);
        Trainee updatedTrainee = traineeRepository.save(trainee);
        return TraineeMapper.toDto(updatedTrainee);
    }

    public void deleteTrainee(Integer id) {
        if (!traineeRepository.existsById(id)) {
            throw new EntityNotFoundException("Trainee not found");
        }
        traineeRepository.deleteById(id);
    }
}
