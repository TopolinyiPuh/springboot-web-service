package by.vstk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "discipline_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discipline_type implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @OneToMany(mappedBy = "type", targetEntity = Discipline.class, cascade = CascadeType.ALL)
    private List<Discipline> discipline;
}
