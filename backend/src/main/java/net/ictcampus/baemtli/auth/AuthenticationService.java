package net.ictcampus.baemtli.auth;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import net.ictcampus.baemtli.auth.dto.AuthUserDTO;
import net.ictcampus.baemtli.team.TeamRepository;
import net.ictcampus.baemtli.user.User;
import net.ictcampus.baemtli.user.UserRepository;
import net.ictcampus.baemtli.user.dto.CreateUserDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, TeamRepository teamRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void signup(@Valid CreateUserDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new EntityExistsException("Username already taken.");
        }

        User user = new User()
                .setUsername(dto.getUsername())
                .setPassword(passwordEncoder.encode(dto.getPassword()))
                .setRole(dto.getRole())
                .setTeam(teamRepository.findById(dto.getTeamId()).orElseThrow(() -> new EntityNotFoundException("Team not found")));

        userRepository.save(user);
    }

    public User authenticate(@Valid AuthUserDTO dto) {
        // Check if username and password match (Spring Security)
        // If they don't match, throw exception (Bad Credentials)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        return userRepository.findByUsername(dto.getUsername()).get();
    }

}
