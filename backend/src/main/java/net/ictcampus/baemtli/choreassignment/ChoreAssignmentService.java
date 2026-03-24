package net.ictcampus.baemtli.choreassignment;

import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.baemtli.choreassignment.dto.ChoreAssignmentDTO;
import net.ictcampus.baemtli.choreassignment.dto.ChoreAssignmentMapper;
import net.ictcampus.baemtli.choreassignment.dto.CreateChoreAssignmentDTO;
import net.ictcampus.baemtli.monthassignment.MonthAssignment;
import net.ictcampus.baemtli.monthassignment.MonthAssignmentRepository;
import net.ictcampus.baemtli.security.AuthorizationService;
import net.ictcampus.baemtli.security.Permission;
import net.ictcampus.baemtli.trainee.Trainee;
import net.ictcampus.baemtli.trainee.TraineeRepository;
import net.ictcampus.baemtli.workday.Workday;
import net.ictcampus.baemtli.workday.WorkdayRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoreAssignmentService {

    private final ChoreAssignmentRepository choreAssignmentRepository;
    private final WorkdayRepository workdayRepository;
    private final MonthAssignmentRepository monthAssignmentRepository;
    private final TraineeRepository traineeRepository;
    private final AuthorizationService authz;

    public ChoreAssignmentService(ChoreAssignmentRepository choreAssignmentRepository,
                                  WorkdayRepository workdayRepository,
                                  MonthAssignmentRepository monthAssignmentRepository,
                                  TraineeRepository traineeRepository,
                                  AuthorizationService authz) {
        this.choreAssignmentRepository = choreAssignmentRepository;
        this.workdayRepository = workdayRepository;
        this.monthAssignmentRepository = monthAssignmentRepository;
        this.traineeRepository = traineeRepository;
        this.authz = authz;
    }

    public List<ChoreAssignmentDTO> getAssignments(Authentication auth) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Permission.DAY_ASSIGNMENT_READ_ALL))) {
            return getAllAssignments();
        } else {
            // User has Permission.DAY_ASSIGNMENT_READ_TEAM
            Integer userTeamId = authz.getTeamId(auth);
            return getAssignmentsByTeam(userTeamId);
        }
    }

    public List<ChoreAssignmentDTO> getAllAssignments() {
        return choreAssignmentRepository.findAll().stream()
                .map(ChoreAssignmentMapper::toDto)
                .toList();
    }

    public List<ChoreAssignmentDTO> getAssignmentsByTeam(Integer teamId) {
        return choreAssignmentRepository.findByMonthAssignmentTeamId(teamId).stream()
                .map(ChoreAssignmentMapper::toDto)
                .toList();
    }

    public ChoreAssignmentDTO createAssignment(CreateChoreAssignmentDTO dto) {
        Workday workday = workdayRepository.findById(dto.getWorkday())
                .orElseThrow(() -> new EntityNotFoundException("Workday not found for date: " + dto.getWorkday()));
        
        MonthAssignment monthAssignment = monthAssignmentRepository.findById(dto.getMonthAssignmentId())
                .orElseThrow(() -> new EntityNotFoundException("Month assignment not found"));

        Trainee trainee = traineeRepository.findById(dto.getTraineeId())
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));

        // Validierung: Gehört der Trainee zum Team, das für diesen Monat eingeteilt ist?
        if (!trainee.getTeam().getId().equals(monthAssignment.getTeam().getId())) {
            throw new IllegalArgumentException("Trainee must belong to the team assigned to this chore");
        }

        ChoreAssignment assignment = new ChoreAssignment();
        assignment.setWorkday(workday);
        assignment.setMonthAssignment(monthAssignment);
        assignment.setTrainee(trainee);

        return ChoreAssignmentMapper.toDto(choreAssignmentRepository.save(assignment));
    }

    public void deleteAssignment(Integer id) {
        if (!choreAssignmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Chore assignment not found");
        }
        choreAssignmentRepository.deleteById(id);
    }
}
