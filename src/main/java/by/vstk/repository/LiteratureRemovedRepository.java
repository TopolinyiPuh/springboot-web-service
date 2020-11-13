package by.vstk.repository;

import by.vstk.model.LiteratureRemoved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LiteratureRemovedRepository extends JpaRepository<LiteratureRemoved, Long> {
    @Modifying
    @Query(value = "INSERT INTO literature SELECT * FROM literature_removed WHERE id = :idLit", nativeQuery = true)
    void insert(@Param("idLit") Long idLit);

    @Modifying
    @Query(value = "DELETE FROM literature_removed WHERE id = :idLit", nativeQuery = true)
    void delete(@Param("idLit") Long idLit);
}
