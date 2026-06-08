package com.example.Historiadb.serviceImpl;

import com.example.Historiadb.model.Editorial;
import com.example.Historiadb.repository.EditorialRepository;
import com.example.Historiadb.service.EditorialService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServiceImpl implements EditorialService {
    private final EditorialRepository editorialRepository;

    public EditorialServiceImpl(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    @Override
    @Transactional
    public Editorial create(Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    @Override
    @Transactional
    public Editorial update(Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Editorial> read(Long id) {
        return editorialRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        editorialRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Editorial> readAll() {
        return editorialRepository.findAll();
    }
}
