package com.example.Historiadb.serviceImpl;

import com.example.Historiadb.model.Idioma;
import com.example.Historiadb.repository.IdiomaRepository;
import com.example.Historiadb.service.IdiomaService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IdiomaServiceImpl implements IdiomaService {
    private final IdiomaRepository idiomaRepository;

    public IdiomaServiceImpl(IdiomaRepository idiomaRepository) {
        this.idiomaRepository = idiomaRepository;
    }

    @Override
    @Transactional
    public Idioma create(Idioma idioma) {
        return idiomaRepository.save(idioma);
    }

    @Override
    @Transactional
    public Idioma update(Idioma idioma) {
        return idiomaRepository.save(idioma);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Idioma> read(Long id) {
        return idiomaRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        idiomaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Idioma> readAll() {
        return idiomaRepository.findAll();
    }
}
