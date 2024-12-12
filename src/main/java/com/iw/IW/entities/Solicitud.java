package com.iw.IW.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "id_u", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_p", nullable = true)
    private Usuario promotor;

    @Column(nullable = false)
    private Long oros; // Dinero solicitado

    @Column(name = "importacia_p", nullable = false)
    private Integer importanciaPromotor; // Nota del 1 al 5

    @Column(columnDefinition = "TEXT", nullable = true)
    private String descripcion;

    @Column(nullable = false, length = 255)
    private String estado;

    @Column(name = "fecha_max", nullable = true)
    private LocalDateTime fechaMaxima;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solicitud solicitud = (Solicitud) o;
        return id != null && id.equals(solicitud.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
