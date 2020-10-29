package by.vstk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "literature")
public class Literature implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false, length = 1)
    @Size(max = 1, message = "Value should be equal to 1")
    private String course;
    @Column(length = 4)
    @Size(min = 2, max = 4, message = "Value should be between 2 and 4")
    private String year;
    private LocalDateTime updated;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Department.class, optional = false)
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Speciality.class, optional = false)
    private Speciality speciality;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Discipline.class, optional = false)
    private Discipline discipline;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = LiteratureType.class, optional = false)
    private LiteratureType literatureType;
    private String docName;
    private String docType;
    @Lob
    private byte[] data;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public String getUserName() {
        return user != null ? user.getUsername() : "none";
    }
}
