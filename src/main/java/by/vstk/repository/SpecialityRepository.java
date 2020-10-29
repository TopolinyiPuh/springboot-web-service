package by.vstk.repository;

import by.vstk.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    @Query(value = "SELECT * FROM speciality WHERE speciality.department_id = :departmentId", nativeQuery = true)
    List<Speciality> findByDepartmentId(@Param("departmentId") Long departmentId);
}
