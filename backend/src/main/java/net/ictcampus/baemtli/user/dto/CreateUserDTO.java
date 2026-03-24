package net.ictcampus.baemtli.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.ictcampus.baemtli.user.Role;

@Getter @Setter
@Accessors(chain = true)
public class CreateUserDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @Size(max = 255, message = "Password must be at most 255 characters long.")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

    @NotNull(message = "Team ID is required")
    private Integer teamId;
}
