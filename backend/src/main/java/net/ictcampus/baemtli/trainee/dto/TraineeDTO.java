package net.ictcampus.baemtli.trainee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer teamId;
}
