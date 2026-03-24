package net.ictcampus.baemtli.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ictcampus.baemtli.user.Role;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private Role role;
    private Integer teamId;
}
