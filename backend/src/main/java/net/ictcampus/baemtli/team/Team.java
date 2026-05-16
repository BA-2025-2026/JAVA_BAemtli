package net.ictcampus.baemtli.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.ictcampus.baemtli.trainee.Trainee;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@Table(name = "team")
@Getter @Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Team")
    private Integer id;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Trainee> trainees;
}
