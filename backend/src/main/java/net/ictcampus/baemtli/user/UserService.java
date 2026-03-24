package net.ictcampus.baemtli.user;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.baemtli.team.TeamRepository;
import net.ictcampus.baemtli.user.dto.UpdateUserDTO;
import net.ictcampus.baemtli.user.dto.UserDTO;
import net.ictcampus.baemtli.user.dto.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TeamRepository teamRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public UserDTO getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.toDto(user);
    }

    public User updateUser(Integer id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Check if updated username is still available
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            if (userRepository.findByUsername(dto.getUsername()).isPresent() && !user.getUsername().equals(dto.getUsername())) {
                throw new EntityExistsException("Username already taken.");
            }
        }

        if (dto.getTeamId() != null) {
            user.setTeam(teamRepository.findById(dto.getTeamId()).orElseThrow(() -> new EntityNotFoundException("Team not found")));
        }

        String encodedPassword = dto.getPassword() != null && !dto.getPassword().isBlank()
                ? passwordEncoder.encode(dto.getPassword())
                : user.getPassword();

        UserMapper.updateFromDto(user, dto, encodedPassword);
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
