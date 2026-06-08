package com.example.Historiadb.controller;

import com.example.Historiadb.model.Libro;
import com.example.Historiadb.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public Iterable<Libro> listar() {
        return libroService.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscar(@PathVariable Long id) {
        return libroService.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Libro crear(@Valid @RequestBody Libro libro) {
        return libroService.create(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @Valid @RequestBody Libro libro) {
        if (libroService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        libro.setIdIsbn(id);
        return ResponseEntity.ok(libroService.update(libro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (libroService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        libroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
