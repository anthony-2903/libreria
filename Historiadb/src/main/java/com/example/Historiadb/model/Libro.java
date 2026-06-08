package com.example.Historiadb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "LIBROS")
public class Libro {
    @Id
    @Column(name = "ID_ISBN")
    private Long idIsbn;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_AUTOR", nullable = false)
    private Autor autor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_EDITORIAL", nullable = false)
    private Editorial editorial;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIAS", nullable = false)
    private Categoria categoria;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_IDIOMAS", nullable = false)
    private Idioma idioma;

    @NotBlank
    @Column(name = "TITULO", nullable = false, length = 200)
    private String titulo;

    @Column(name = "STOCK")
    private Long stock;

    @Column(name = "FECHA_PUBLICACION")
    private LocalDate fechaPublicacion;
}
