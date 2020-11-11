package by.vstk.repository;

import by.vstk.model.LiteratureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LiteratureTypeRepository extends JpaRepository<LiteratureType, Long> {

    @Query(value = "SELECT * FROM literature_type WHERE literature_type.id BETWEEN :first AND :second", nativeQuery = true)
    List<LiteratureType> findByCategory(@Param("first") Long first, @Param("second") Long second);
}
