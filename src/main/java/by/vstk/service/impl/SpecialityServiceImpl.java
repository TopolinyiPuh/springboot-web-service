package by.vstk.service.impl;

import by.vstk.model.Department;
import by.vstk.model.Speciality;
import by.vstk.repository.DepartmentRepository;
import by.vstk.repository.SpecialityRepository;
import by.vstk.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityServiceImpl implements SpecialityService {
    private final SpecialityRepository specRepo;
    private final DepartmentRepository depRepo;

    @Autowired
    public SpecialityServiceImpl(SpecialityRepository specRepo, DepartmentRepository depRepo) {
        this.specRepo = specRepo;
        this.depRepo = depRepo;
    }

    @Override
    public void create(Speciality speciality, Long departmentId) {
        Department department = depRepo.getOne(departmentId);
        speciality.setDepartment(department);
        specRepo.save(speciality);
    }

    @Override
    public List<Speciality> getSpecialitiesByDep(Long departmentId) { return specRepo.findByDepartmentId(departmentId); }

    @Override
    public List<Speciality> getSpecialities() { return specRepo.findAll(); }
}
