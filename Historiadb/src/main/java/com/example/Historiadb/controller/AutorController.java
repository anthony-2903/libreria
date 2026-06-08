package com.example.Historiadb.controller;

import com.example.Historiadb.model.Autor;
import com.example.Historiadb.service.AutorService;
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
@RequestMapping("/api/autores")
public class AutorController {
    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public Iterable<Autor> listar() {
        return autorService.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscar(@PathVariable Long id) {
        return autorService.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Autor crear(@Valid @RequestBody Autor autor) {
        return autorService.create(autor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizar(@PathVariable Long id, @Valid @RequestBody Autor autor) {
        if (autorService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autor.setIdAutor(id);
        return ResponseEntity.ok(autorService.update(autor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (autorService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
