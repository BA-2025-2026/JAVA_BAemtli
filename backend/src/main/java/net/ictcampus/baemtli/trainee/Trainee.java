package net.ictcampus.baemtli.trainee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.ictcampus.baemtli.team.Team;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "trainee")
@Getter @Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Trainee")
    private Integer id;

    @Column(name = "FirstName", nullable = false, length = 30)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 30)
    private String lastName;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Team_ID", nullable = false)
    private Team team;
}
