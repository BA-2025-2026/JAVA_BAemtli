package net.ictcampus.baemtli.monthassignment;

import jakarta.persistence.*;
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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Month_ID", nullable = false)
    private Month month;
}
