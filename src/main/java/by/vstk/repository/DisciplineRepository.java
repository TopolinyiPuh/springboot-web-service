package by.vstk.repository;

import by.vstk.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    @Query(value = "SELECT * FROM discipline WHERE discipline.specialityId = :specialityId AND discipline.course = :course", nativeQuery = true)
    List<Discipline> findBySpecialityAndCourse(@Param("specialityId") Long specialityId, @Param("course") String course);

    @Query(value = "SELECT * FROM discipline WHERE discipline.speciality_id = :specialityId", nativeQuery = true)
    List<Discipline> findBySpeciality(@Param("specialityId") Long specialityId);

    @Query(value = "SELECT * FROM discipline WHERE discipline.course = :course", nativeQuery = true)
    List<Discipline> findByCourse(@Param("course") String course);
}
