package by.vstk.service;

import by.vstk.model.Department;
import by.vstk.model.Literature;
import by.vstk.model.LiteratureType;
import by.vstk.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LiteratureService {

    List<Literature> getAll();
    Page<Literature> getAll(Pageable pageable);
    Page<Literature> getPaginated(Pageable pageable);

    Literature getFile(Long id);

    void create(Literature literature, Long departmentId, Long specialityId, Long disciplineId, Long typeId, MultipartFile[] files, User user) throws IOException;

    List<Department> getDepartments();
    List<LiteratureType> getTypes();
    List<LiteratureType> getByCategory(Long first, Long second);

    List<Literature> getByDisciplineAndType(Long DisciplineId, Long typeId);

    List<Literature> getBySpeciality(Long specialityId);
    List<Literature> getByDiscipline(Long disciplineId);
    List<Literature> getByCourse(String course);

    void insert(Long id);
    void delete(Long id);

    boolean isExists(Long id);
}
