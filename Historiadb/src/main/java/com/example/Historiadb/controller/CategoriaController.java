package com.example.Historiadb.controller;

import com.example.Historiadb.model.Categoria;
import com.example.Historiadb.service.CategoriaService;
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
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public Iterable<Categoria> listar() {
        return categoriaService.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscar(@PathVariable Long id) {
        return categoriaService.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Categoria crear(@Valid @RequestBody Categoria categoria) {
        return categoriaService.create(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
        if (categoriaService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoria.setIdCategorias(id);
        return ResponseEntity.ok(categoriaService.update(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (categoriaService.read(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
