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
import java.util.Optional;

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
        // Try to locate the user
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Validate new username
        Optional.ofNullable(dto.getUsername())
                .filter(String::isBlank)
                .filter(n -> n.length() > 50)
                .ifPresent(n -> {throw new IllegalArgumentException("Username needs to be between 1-50 characters."); });

        // Check if username is still available
        Optional.ofNullable(dto.getUsername())
                .filter(n -> userRepository.findByUsername(n).isPresent())
                .ifPresent(n -> {throw new EntityExistsException("Username already taken."); });

        // Check if provided team id exists
        Optional.ofNullable(dto.getTeamId())
                .filter(i -> teamRepository.findById(i).isEmpty())
                .ifPresent(i -> {throw new EntityNotFoundException("No team found for provided team id."); });

        // Encode Password, if provided. Else use existing password hash.
        String encodedPassword = Optional.ofNullable(dto.getPassword())
                .filter(p -> !p.isBlank())
                .map(passwordEncoder::encode)
                .orElse(user.getPassword());

        // Map values from dto to user object
        UserMapper.updateFromDto(user, dto, encodedPassword);

        // Write to db and return updated user object
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
