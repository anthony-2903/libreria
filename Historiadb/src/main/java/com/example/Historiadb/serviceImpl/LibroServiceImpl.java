package com.example.Historiadb.serviceImpl;

import com.example.Historiadb.model.Libro;
import com.example.Historiadb.repository.LibroRepository;
import com.example.Historiadb.service.LibroService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServiceImpl implements LibroService {
    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    @Transactional
    public Libro create(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    @Transactional
    public Libro update(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Libro> read(Long id) {
        return libroRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        libroRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> readAll() {
        return libroRepository.findAll();
    }
}
