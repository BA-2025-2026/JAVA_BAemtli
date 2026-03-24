package net.ictcampus.baemtli.choreassignment;

import jakarta.persistence.*;
import lombok.Data;
import net.ictcampus.baemtli.monthassignment.MonthAssignment;
import net.ictcampus.baemtli.trainee.Trainee;
import net.ictcampus.baemtli.workday.Workday;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "choreassignment")
@Data
public class ChoreAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Choreassignment")
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Workday_ID", nullable = false)
    private Workday workday;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Monthassignment_ID", nullable = false)
    private MonthAssignment monthAssignment;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Trainee_ID")
    private Trainee trainee;
}
