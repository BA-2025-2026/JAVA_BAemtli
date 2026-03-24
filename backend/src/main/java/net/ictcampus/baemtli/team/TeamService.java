package net.ictcampus.baemtli.team;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.baemtli.team.dto.CreateTeamDTO;
import net.ictcampus.baemtli.team.dto.TeamDTO;
import net.ictcampus.baemtli.team.dto.TeamMapper;
import net.ictcampus.baemtli.team.dto.UpdateTeamDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamMapper::toDto)
                .toList();
    }

    public TeamDTO getTeamById(Integer id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        return TeamMapper.toDto(team);
    }

    public TeamDTO createTeam(CreateTeamDTO dto) {
        if (teamRepository.findByName(dto.getName()).isPresent()) {
            throw new EntityExistsException("Team with this name already exists");
        }

        Team team = new Team()
                .setName(dto.getName());

        Team savedTeam = teamRepository.save(team);
        return TeamMapper.toDto(savedTeam);
    }

    public TeamDTO updateTeam(Integer id, UpdateTeamDTO dto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            teamRepository.findByName(dto.getName())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(id)) {
                            throw new EntityExistsException("Team with this name already exists");
                        }
                    });
            team.setName(dto.getName());
        }

        Team savedTeam = teamRepository.save(team);
        return TeamMapper.toDto(savedTeam);
    }

    public void deleteTeam(Integer id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team not found");
        }
        try {
            teamRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalStateException("Team cannot be deleted as it is still in use", e);
        }
    }
}
