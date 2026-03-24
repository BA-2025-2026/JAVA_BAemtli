package net.ictcampus.baemtli.monthassignment;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "month")
@Data
public class Month {
    @Id
    @Column(name = "ID_Month")
    private Integer id;

    @Column(name = "Month")
    private Integer month;

    @Column(name = "Year", nullable = false, columnDefinition = "YEAR")
    private Integer year;
}
