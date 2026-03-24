package net.ictcampus.baemtli.user.dto;

import net.ictcampus.baemtli.user.User;

public class UserMapper {

    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getTeam() != null ? user.getTeam().getId() : null
        );
    }

    public static void updateFromDto(User user, UpdateUserDTO dto, String encodedPassword) {
        // Always check null first, else .isBlank would give NPE if variable is null
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(encodedPassword);
        }
        
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
    }
}
