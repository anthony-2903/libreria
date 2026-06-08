package com.example.Historiadb.controller;

import com.example.Historiadb.model.Idioma;
import com.example.Historiadb.service.IdiomaService;
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
@RequestMapping("/api/idiomas")
public class IdiomaController {
    private final IdiomaService idiomaService;

    public IdiomaController(IdiomaService idiomaService) {
        this.idiomaService = idiomaService;
    }

    @GetMapping
    public Iterable<Idioma> listar() {
        return idiomaService.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idioma> buscar(@PathVariable Long id) {
        return idiomaService.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Idioma crear(@Valid @RequestBody Idioma idioma) {
        return idiomaService.create(idioma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idioma> actualizar(@PathVariable Long id, @Valid @RequestBody Idioma idioma) {
        if (idiomaService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        idioma.setIdIdiomas(id);
        return ResponseEntity.ok(idiomaService.update(idioma));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (idiomaService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        idiomaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
