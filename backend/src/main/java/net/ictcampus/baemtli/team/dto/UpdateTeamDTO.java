package net.ictcampus.baemtli.team.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeamDTO {
    @Size(max = 30, message = "Name must be at most 30 characters")
    private String name;
}
