package com.example.Historiadb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "AUTOR")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUTOR")
    private Long idAutor;

    @NotBlank
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "ID_PAIS")
    private Long idPais;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "WEBSITE", length = 100)
    private String website;

    @Column(name = "ACERCA_DE", length = 500)
    private String acercaDe;
}
