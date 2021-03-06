package by.vstk.service.impl;

import by.vstk.model.*;
import by.vstk.repository.*;
import by.vstk.service.LiteratureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class LiteratureServiceImpl implements LiteratureService {
    private final LiteratureRepository litRepo;
    private final DepartmentRepository depRepo;
    private final SpecialityRepository specRepo;
    private final DisciplineRepository discipRepo;
    private final LiteratureTypeRepository typeRepo;
    private final LiteratureSearch searchService;

    @Autowired
    public LiteratureServiceImpl(LiteratureRepository litRepo, DepartmentRepository depRepo, SpecialityRepository specRepo, DisciplineRepository discipRepo, LiteratureTypeRepository typeRepo, LiteratureSearch searchService) {
        this.litRepo = litRepo;
        this.depRepo = depRepo;
        this.specRepo = specRepo;
        this.discipRepo = discipRepo;
        this.typeRepo = typeRepo;
        this.searchService = searchService;
    }

    @Override
    public List<Literature> getAll() {
        return litRepo.findAll();
    }

    @Override
    public Page<Literature> getAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Literature> literature = litRepo.findAll();
        List<Literature> list;

        if (literature.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, literature.size());
            list = literature.subList(startItem, toIndex);
        }

        Page<Literature> literaturePage = new PageImpl<Literature>(list, PageRequest.of(currentPage, pageSize), literature.size());

        return literaturePage;
    }

    @Override
    public Page<Literature> getPaginated(Pageable pageable) {
        return null;
    }

    @Override
    public Literature getFile(Long id) {
        return litRepo.getOne(id);
    }

    @Override
    public void create(Literature literature, Long departmentId, Long specialityId, Long disciplineId, Long typeId, MultipartFile[] files, User user) throws IOException {
        Department department = depRepo.getOne(departmentId);
        Speciality speciality = specRepo.getOne(specialityId);
        Discipline discipline = discipRepo.getOne(disciplineId);
        LiteratureType type = typeRepo.getOne(typeId);

        for (MultipartFile file : files) {
            literature.setTitle(file.getOriginalFilename());
            literature.setDocName(file.getOriginalFilename());
            literature.setDocType(file.getContentType());
            literature.setData(file.getBytes());
            literature.setDepartment(department);
            literature.setSpeciality(speciality);
            literature.setDiscipline(discipline);
            literature.setLiteratureType(type);
            literature.setUpdated(LocalDateTime.now());
            literature.setUser(user);
            litRepo.save(literature);
        }
    }

    @Override
    public void update(Long id, Long departmentId, Long specialityId, Long disciplineId, Long typeId, MultipartFile[] files, User user) throws IOException {
       Literature literatureToUpdate = litRepo.getOne(id);
        Department department = depRepo.getOne(departmentId);
        Speciality speciality = specRepo.getOne(specialityId);
        Discipline discipline = discipRepo.getOne(disciplineId);
        LiteratureType type = typeRepo.getOne(typeId);

        for (MultipartFile file : files) {
            literatureToUpdate.setDocName(file.getOriginalFilename());
            literatureToUpdate.setDocType(file.getContentType());
            literatureToUpdate.setData(file.getBytes());
            literatureToUpdate.setDepartment(department);
            literatureToUpdate.setSpeciality(speciality);
            literatureToUpdate.setDiscipline(discipline);
            literatureToUpdate.setLiteratureType(type);
            literatureToUpdate.setUpdated(LocalDateTime.now());
            literatureToUpdate.setUser(user);
            litRepo.save(literatureToUpdate);
        }

    }

    @Override
    public List<Department> getDepartments() {
        return depRepo.findAll();
    }

    @Override
    public List<LiteratureType> getTypes() {
        return typeRepo.findAll();
    }

    @Override
    public List<LiteratureType> getByCategory(Long first, Long second) {
        return typeRepo.findByCategory(first, second);
    }

    @Override
    public List<Literature> getByDisciplineAndType(Long disciplineId, Long typeId) {
        return litRepo.findByDisciplineAndType(disciplineId, typeId);
    }

    @Override
    public List<Literature> getByDisciplineAndTypes(Long disciplineId) {
        return litRepo.findByDisciplineAndTypes(disciplineId);
    }

    @Override
    public List<Literature> getBySpeciality(Long specialityId) {
        return litRepo.findBySpecialityId(specialityId);
    }

    @Override
    public List<Literature> getByDiscipline(Long disciplineId) {
        return litRepo.findByDisciplineId(disciplineId);
    }

    @Override
    public List<Literature> getByCourse(String course) {
        return litRepo.findByCourse(course);
    }

    @Override
    public void insert(Long id) {
        litRepo.insert(id);
    }

    @Override
    public void delete(Long id) {
        litRepo.delete(id);
    }

    @Override
    public boolean isExists(Long id) {
        return depRepo.existsById(id);
    }
}
