package by.vstk.repository;

import by.vstk.model.Literature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LiteratureRepository extends JpaRepository<Literature, Long> {

    List<Literature> findByDisciplineId(Long disciplineId);
    List<Literature> findByDepartmentId(Long departmentId);
    List<Literature> findByLiteratureTypeId(Long literatureTypeId);
    List<Literature> findBySpecialityId(Long specialityId);
    List<Literature> findByCourse(String course);
    @Query(value = "SELECT * FROM literature WHERE literature.discipline_id = :discipline AND literature.literature_type_id = :type", nativeQuery = true)
    List<Literature> findByDisciplineAndType(@Param("discipline") Long disciplineId, @Param("type") Long typeId);

    @Modifying
    @Query(value = "INSERT INTO literature_removed SELECT * FROM literature WHERE id = :idLit", nativeQuery = true)
    void insert(@Param("idLit") Long idLit);

    @Modifying
    @Query(value = "DELETE FROM literature WHERE id = :idLit", nativeQuery = true)
    void delete(@Param("idLit") Long idLit);
}
