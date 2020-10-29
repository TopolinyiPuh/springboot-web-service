package by.vstk.service;

import by.vstk.model.Discipline;

import java.util.List;

public interface DisciplineService {

    List<Discipline> getDisciplines();

    List<Discipline> getDisciplinesBySpecialityAndCourse(Long specialityId, String course);

    List<Discipline> getDisciplineListByCourse(List<Discipline> disciplineList, String course);

    List<Discipline> getDisciplinesBySpeciality(Long specialityId);

    List<Discipline> getDisciplinesByCourse(String course);
}
