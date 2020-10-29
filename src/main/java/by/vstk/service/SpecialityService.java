package by.vstk.service;

import by.vstk.model.Speciality;

import java.util.List;

public interface SpecialityService {

    void create(Speciality speciality, Long departmentId);

    List<Speciality> getSpecialitiesByDep(Long departmentId);

    List<Speciality> getSpecialities();
}
