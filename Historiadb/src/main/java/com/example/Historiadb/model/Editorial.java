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
@Table(name = "EDITORIAL")
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EDITORIAL")
    private Long idEditorial;

    @NotBlank
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @Column(name = "ID_PAIS")
    private Long idPais;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "FAX", length = 20)
    private String fax;

    @Column(name = "WEBSITE", length = 100)
    private String website;

    @Column(name = "CP", length = 20)
    private String cp;
}
