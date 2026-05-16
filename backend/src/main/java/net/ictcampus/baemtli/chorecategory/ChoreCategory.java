package net.ictcampus.baemtli.chorecategory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "chorecategory")
@Getter @Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ChoreCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Chorecategory")
    private Integer id;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;
}
