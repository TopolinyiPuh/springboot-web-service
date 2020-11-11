package by.vstk.repository;

import by.vstk.model.Literature;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query(value = "SELECT l.id, l.author, l.data, l.doc_name, l.doc_type, l.title, l.updated, l.year, d.id, d.title AS discipline_title, \n" +
            "s.title AS speciality_title, u.username AS username\n" +
            "FROM literature l \n" +
            "INNER JOIN discipline d ON d.id = l.discipline_id \n" +
            "INNER JOIN speciality s ON s.id = l.speciality_id\n" +
            "INNER JOIN t_user u ON u.id = l.user_id WHERE d.title LIKE '%' + :word + '%' OR s.title LIKE '%' + :word + '%' OR u.username LIKE '%' + :word + '%'", nativeQuery = true)
    List<Literature> findBy(@Param("word") String word);
}
