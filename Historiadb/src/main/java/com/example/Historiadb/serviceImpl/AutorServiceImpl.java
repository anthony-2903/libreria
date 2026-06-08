package com.example.Historiadb.serviceImpl;

import com.example.Historiadb.model.Autor;
import com.example.Historiadb.repository.AutorRepository;
import com.example.Historiadb.service.AutorService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServiceImpl implements AutorService {
    private final AutorRepository autorRepository;

    public AutorServiceImpl(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    @Transactional
    public Autor create(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    @Transactional
    public Autor update(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Autor> read(Long id) {
        return autorRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        autorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> readAll() {
        return autorRepository.findAll();
    }
}
