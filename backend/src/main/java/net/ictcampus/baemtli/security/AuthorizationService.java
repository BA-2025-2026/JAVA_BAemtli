package net.ictcampus.baemtli.security;

import net.ictcampus.baemtli.choreassignment.ChoreAssignmentRepository;
import net.ictcampus.baemtli.monthassignment.MonthAssignmentRepository;
import net.ictcampus.baemtli.trainee.TraineeRepository;
import net.ictcampus.baemtli.user.UserRepository;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Prüft Berechtigungen auf Datensatz-Ebene (z. B. "Darf User A den User B löschen?")
 */
@Component("authz")
@EnableMethodSecurity
public class AuthorizationService {

    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final MonthAssignmentRepository monthAssignmentRepository;
    private final ChoreAssignmentRepository choreAssignmentRepository;

    public AuthorizationService(UserRepository userRepository, 
                                TraineeRepository traineeRepository,
                                MonthAssignmentRepository monthAssignmentRepository,
                                ChoreAssignmentRepository choreAssignmentRepository) {
        this.userRepository = userRepository;
        this.traineeRepository = traineeRepository;
        this.monthAssignmentRepository = monthAssignmentRepository;
        this.choreAssignmentRepository = choreAssignmentRepository;
    }

    public boolean canAccessUser(Authentication auth, Integer userId) {
        return userRepository.findByUsername(auth.getName())
                .map(current -> current.getId().equals(userId))
                .orElse(false);
    }

    public boolean isCoach(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_Coach"));
    }

    public boolean isInTeam(Authentication auth, Integer teamId) {
        return userRepository.findByUsername(auth.getName())
                .map(current -> current.getTeam() != null && current.getTeam().getId().equals(teamId))
                .orElse(false);
    }

    public Integer getTeamId(Authentication auth) {
        return userRepository.findByUsername(auth.getName())
                .map(current -> current.getTeam() != null ? current.getTeam().getId() : null)
                .orElse(null);
    }

    public boolean canAccessTrainee(Authentication auth, Integer traineeId) {
        if (isCoach(auth)) return true;
        return traineeRepository.findById(traineeId)
                .map(trainee -> trainee.getTeam() != null && isInTeam(auth, trainee.getTeam().getId()))
                .orElse(false);
    }

    public boolean isInMonthAssignmentTeam(Authentication auth, Integer monthAssignmentId) {
        if (isCoach(auth)) return true;
        return monthAssignmentRepository.findById(monthAssignmentId)
                .map(ma -> isInTeam(auth, ma.getTeam().getId()))
                .orElse(false);
    }

    public boolean canAccessChoreAssignment(Authentication auth, Integer choreAssignmentId) {
        if (isCoach(auth)) return true;
        return choreAssignmentRepository.findById(choreAssignmentId)
                .map(ca -> isInMonthAssignmentTeam(auth, ca.getMonthAssignment().getId()))
                .orElse(false);
    }
}
