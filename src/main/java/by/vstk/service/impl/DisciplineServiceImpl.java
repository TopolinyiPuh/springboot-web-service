package by.vstk.service.impl;

import by.vstk.model.Discipline;
import by.vstk.repository.DisciplineRepository;
import by.vstk.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService {
    private final DisciplineRepository discipRepo;

    @Autowired
    public DisciplineServiceImpl(DisciplineRepository discipRepo) {
        this.discipRepo = discipRepo;
    }

    @Override
    public List<Discipline> getDisciplines() {
        return discipRepo.findAll();
    }

    @Override
    public List<Discipline> getDisciplinesBySpecialityAndCourse(Long specialityId, String course) {
        List<Discipline> disciplineList = discipRepo.findBySpeciality(specialityId);
        List<Discipline> disciplines = new ArrayList<>();
        for (Discipline discipline : disciplineList) {
            if(discipline.getCourse().equals(course))
                disciplines.add(discipline);
        }
        return disciplines;
//        return discipRepo.findBySpecialityAndCourse(specialityId, course);
    }

    @Override
    public List<Discipline> getDisciplineListByCourse(List<Discipline> disciplineList, String course) {
        List<Discipline> disciplines = new ArrayList<>();
        for (Discipline discipline : disciplineList) {
            if(discipline.getCourse().equals(course))
                disciplines.add(discipline);
        }
        return disciplines;
//        return discipRepo.findBySpecialityAndCourse(specialityId, course);
    }

    @Override
    public List<Discipline> getDisciplinesBySpeciality(Long specialityId, Long typeId) {
        return discipRepo.findBySpecialityAndType(specialityId, typeId);
    }

    @Override
    public List<Discipline> getDisciplinesByCourse(String course) {
        return discipRepo.findByCourse(course);
    }
}
