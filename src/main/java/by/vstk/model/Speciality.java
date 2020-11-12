package by.vstk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "speciality")
public class Speciality implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    @Field
    private String title;
    @Column(nullable = false)
    @Size(min = 8, max = 20, message = "Value should be between 8 and 20")
    private String code;
    @OneToMany(mappedBy = "speciality", targetEntity = Literature.class, cascade = CascadeType.ALL)
    private List<Literature> literature;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Department.class, optional = false)
    private Department department;
    @OneToMany(mappedBy = "speciality", targetEntity = Discipline.class)
    private List<Discipline> discipline;
}
