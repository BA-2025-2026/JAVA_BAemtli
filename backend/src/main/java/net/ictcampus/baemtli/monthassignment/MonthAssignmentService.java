package net.ictcampus.baemtli.monthassignment;

import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.baemtli.chorecategory.ChoreCategory;
import net.ictcampus.baemtli.chorecategory.ChoreCategoryRepository;
import net.ictcampus.baemtli.monthassignment.dto.CreateMonthAssignmentDTO;
import net.ictcampus.baemtli.monthassignment.dto.MonthAssignmentDTO;
import net.ictcampus.baemtli.monthassignment.dto.MonthAssignmentMapper;
import net.ictcampus.baemtli.team.Team;
import net.ictcampus.baemtli.team.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthAssignmentService {

    private final MonthAssignmentRepository monthAssignmentRepository;
    private final TeamRepository teamRepository;
    private final ChoreCategoryRepository choreCategoryRepository;
    private final MonthRepository monthRepository;

    public MonthAssignmentService(MonthAssignmentRepository monthAssignmentRepository,
                                   TeamRepository teamRepository,
                                   ChoreCategoryRepository choreCategoryRepository,
                                   MonthRepository monthRepository) {
        this.monthAssignmentRepository = monthAssignmentRepository;
        this.teamRepository = teamRepository;
        this.choreCategoryRepository = choreCategoryRepository;
        this.monthRepository = monthRepository;
    }

    public List<MonthAssignmentDTO> getAllAssignments() {
        return monthAssignmentRepository.findAll().stream()
                .map(MonthAssignmentMapper::toDto)
                .toList();
    }

    public MonthAssignmentDTO createAssignment(CreateMonthAssignmentDTO dto) {
        if (monthAssignmentRepository.findByTeamIdAndChoreCategoryIdAndMonthId(dto.getTeamId(), dto.getChoreCategoryId(), dto.getMonthId()).isPresent()) {
            throw new jakarta.persistence.EntityExistsException("Identical monthly assignment already exists");
        }

        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        ChoreCategory category = choreCategoryRepository.findById(dto.getChoreCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Chore category not found"));
        Month month = monthRepository.findById(dto.getMonthId())
                .orElseThrow(() -> new EntityNotFoundException("Month not found"));

        MonthAssignment assignment = new MonthAssignment();
        assignment.setTeam(team);
        assignment.setChoreCategory(category);
        assignment.setMonth(month);

        return MonthAssignmentMapper.toDto(monthAssignmentRepository.save(assignment));
    }

    public MonthAssignmentDTO updateAssignment(Integer id, net.ictcampus.baemtli.monthassignment.dto.UpdateMonthAssignmentDTO dto) {
        MonthAssignment assignment = monthAssignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Month assignment not found"));

        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team not found"));
            assignment.setTeam(team);
        }
        if (dto.getChoreCategoryId() != null) {
            ChoreCategory category = choreCategoryRepository.findById(dto.getChoreCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Chore category not found"));
            assignment.setChoreCategory(category);
        }
        if (dto.getMonthId() != null) {
            Month month = monthRepository.findById(dto.getMonthId())
                    .orElseThrow(() -> new EntityNotFoundException("Month not found"));
            assignment.setMonth(month);
        }

        // Check for duplicates after modification
        monthAssignmentRepository.findByTeamIdAndChoreCategoryIdAndMonthId(
                assignment.getTeam().getId(),
                assignment.getChoreCategory().getId(),
                assignment.getMonth().getId()
        ).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new jakarta.persistence.EntityExistsException("Identical monthly assignment already exists");
            }
        });

        return MonthAssignmentMapper.toDto(monthAssignmentRepository.save(assignment));
    }

    public void deleteAssignment(Integer id) {
        if (!monthAssignmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Month assignment not found");
        }
        monthAssignmentRepository.deleteById(id);
    }
}
