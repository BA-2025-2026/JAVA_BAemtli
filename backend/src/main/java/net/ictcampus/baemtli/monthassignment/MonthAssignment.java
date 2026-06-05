package net.ictcampus.baemtli.monthassignment;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import net.ictcampus.baemtli.chorecategory.ChoreCategory;
import net.ictcampus.baemtli.team.Team;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "monthassignment")
@Data
public class MonthAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Monthassignment")
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Team_ID", nullable = false)
    private Team team;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Chorecategory_ID", nullable = false)
    private ChoreCategory choreCategory;

    @Column(name = "monthInt", nullable = false)
    @Min(value = 1, message = "Month Integer needs to be at least 1.")
    @Max(value = 12, message = "Month Integer cannot be bigger than 12.")
    private Integer monthInt;
}
