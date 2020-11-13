package by.vstk.service.impl;

import by.vstk.model.LiteratureRemoved;
import by.vstk.repository.LiteratureRemovedRepository;
import by.vstk.service.LiteratureRemovedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service
public class LiteratureRemovedServiceImpl implements LiteratureRemovedService {
    private final LiteratureRemovedRepository repository;

    @Autowired
    public LiteratureRemovedServiceImpl(LiteratureRemovedRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LiteratureRemoved> getAll() {
        return repository.findAll();
    }

    @Override
    public void insert(Long id) {
        if (!isExists(id)) {
            throw new EntityNotFoundException("Literature whit id: " + id + "wasn't found!");
        }
        repository.insert(id);
    }

    @Override
    public void delete(Long id) {
        if (!isExists(id)) {
            throw new EntityNotFoundException("Literature whit id: " + id + "wasn't found!");
        }
        repository.delete(id);
    }

    @Override
    public boolean isExists(Long id) {
        return repository.existsById(id);
    }
}
