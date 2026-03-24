package net.ictcampus.baemtli.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ictcampus.baemtli.user.Role;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    // fields optional since user might only want to update certain fields
    private String username;
    private String password;
    private Role role;
    private Integer teamId;
}
