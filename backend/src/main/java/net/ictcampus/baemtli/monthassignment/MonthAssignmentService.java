package net.ictcampus.baemtli.monthassignment;

import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.baemtli.chorecategory.ChoreCategory;
import net.ictcampus.baemtli.chorecategory.ChoreCategoryRepository;
import net.ictcampus.baemtli.monthassignment.dto.CreateMonthAssignmentDTO;
import net.ictcampus.baemtli.monthassignment.dto.MonthAssignmentDTO;
import net.ictcampus.baemtli.monthassignment.dto.MonthAssignmentMapper;
import net.ictcampus.baemtli.monthassignment.dto.UpdateMonthAssignmentDTO;
import net.ictcampus.baemtli.team.Team;
import net.ictcampus.baemtli.team.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthAssignmentService {

    private final MonthAssignmentRepository monthAssignmentRepository;
    private final TeamRepository teamRepository;
    private final ChoreCategoryRepository choreCategoryRepository;

    public MonthAssignmentService(MonthAssignmentRepository monthAssignmentRepository,
                                   TeamRepository teamRepository,
                                   ChoreCategoryRepository choreCategoryRepository) {
        this.monthAssignmentRepository = monthAssignmentRepository;
        this.teamRepository = teamRepository;
        this.choreCategoryRepository = choreCategoryRepository;
    }

    public List<MonthAssignmentDTO> getAllAssignments() {
        return monthAssignmentRepository.findAll().stream()
                .map(MonthAssignmentMapper::toDto)
                .toList();
    }

    public MonthAssignmentDTO createAssignment(CreateMonthAssignmentDTO dto) {
        if (monthAssignmentRepository.findByTeamIdAndChoreCategoryIdAndMonthInt(dto.getTeamId(), dto.getChoreCategoryId(), dto.getMonthInt()).isPresent()) {
            throw new jakarta.persistence.EntityExistsException("Identical monthly assignment already exists");
        }

        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        ChoreCategory category = choreCategoryRepository.findById(dto.getChoreCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Chore category not found"));

        MonthAssignment assignment = new MonthAssignment();
        assignment.setTeam(team);
        assignment.setChoreCategory(category);
        assignment.setMonthInt(dto.getMonthInt());

        return MonthAssignmentMapper.toDto(monthAssignmentRepository.save(assignment));
    }

    public MonthAssignmentDTO updateAssignment(Integer id, UpdateMonthAssignmentDTO dto) {
        MonthAssignment assignment = monthAssignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Month assignment not found"));

        if (dto.getTeamId() != null) {
            // Check if team exists
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team not found"));
            // Update assignment entry
            assignment.setTeam(team);
        }
        if (dto.getChoreCategoryId() != null) {
            // Check if chorecategory exists
            ChoreCategory category = choreCategoryRepository.findById(dto.getChoreCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Chore category not found"));
            // Update assignment entity
            assignment.setChoreCategory(category);
        }

        // Check for duplicates after modification
        monthAssignmentRepository.findByTeamIdAndChoreCategoryIdAndMonthInt(
                assignment.getTeam().getId(),
                assignment.getChoreCategory().getId(),
                assignment.getMonthInt()
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
