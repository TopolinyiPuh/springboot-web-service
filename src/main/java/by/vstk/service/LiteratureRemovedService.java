package by.vstk.service;

import by.vstk.model.LiteratureRemoved;

import java.util.List;

public interface LiteratureRemovedService {

    List<LiteratureRemoved> getAll();

    void insert(Long id);

    void delete(Long id);

    boolean isExists(Long id);
}
