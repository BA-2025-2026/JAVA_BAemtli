package net.ictcampus.baemtli.workday;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "workday")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workday {
    @Id
    @Column(name = "ID_Workday")
    private LocalDate date;
}
