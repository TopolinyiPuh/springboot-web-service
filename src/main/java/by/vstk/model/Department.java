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
@Table(name = "department")
public class Department implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 3)
    private String attachment;
    @OneToMany(mappedBy = "department", targetEntity = Literature.class, cascade = CascadeType.ALL)
    private List<Literature> literature;
    @OneToMany(mappedBy = "department", targetEntity = Speciality.class, cascade = CascadeType.ALL)
    private List<Speciality> specialities;
}
