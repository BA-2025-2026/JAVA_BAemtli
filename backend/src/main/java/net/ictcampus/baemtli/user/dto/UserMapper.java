package net.ictcampus.baemtli.user.dto;

import net.ictcampus.baemtli.team.Team;
import net.ictcampus.baemtli.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserMapper {

    public static UserDTO toDto(User user) {
        Integer teamId = Optional.ofNullable(user.getTeam())
                .map(Team::getId)
                .orElse(null);

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                teamId
        );
    }

    public static void updateFromDto(User user, UpdateUserDTO dto, String encodedPassword) {
        Optional.ofNullable(dto.getUsername())
                .filter(n -> !n.isBlank())
                .ifPresent(user::setUsername);

        Optional.ofNullable(dto.getPassword())
                .filter(p -> !p.isBlank())
                .ifPresent(p -> user.setPassword(encodedPassword));

        Optional.ofNullable(dto.getRole())
                .ifPresent(user::setRole);
    }
}
