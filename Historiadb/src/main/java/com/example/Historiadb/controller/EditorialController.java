package com.example.Historiadb.controller;

import com.example.Historiadb.model.Editorial;
import com.example.Historiadb.service.EditorialService;
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
@RequestMapping("/api/editoriales")
public class EditorialController {
    private final EditorialService editorialService;

    public EditorialController(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    @GetMapping
    public Iterable<Editorial> listar() {
        return editorialService.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editorial> buscar(@PathVariable Long id) {
        return editorialService.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Editorial crear(@Valid @RequestBody Editorial editorial) {
        return editorialService.create(editorial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Editorial> actualizar(@PathVariable Long id, @Valid @RequestBody Editorial editorial) {
        if (editorialService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        editorial.setIdEditorial(id);
        return ResponseEntity.ok(editorialService.update(editorial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (editorialService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        editorialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
