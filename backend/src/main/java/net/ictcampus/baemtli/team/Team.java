package net.ictcampus.baemtli.team;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

}
