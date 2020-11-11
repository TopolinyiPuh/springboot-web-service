package by.vstk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discipline")
public class Discipline implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String course;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Discipline_type.class, cascade = CascadeType.ALL)
    private Discipline_type type;
    @OneToMany(mappedBy = "discipline", targetEntity = Literature.class, cascade = CascadeType.ALL)
    private List<Literature> literature;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Speciality.class, cascade = CascadeType.ALL)
    private Speciality speciality;
}
