package com.iw.IW.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_s", nullable = false)
    private Solicitud solicitud; // Proyecto derivado de una solicitud

    @Column(columnDefinition = "TEXT", nullable = true)
    private String descripcion;

    @Column(nullable = false, length = 255)
    private String estado;

    @Column(name = "recursosh", nullable = false)
    private Integer recursosHumanos;

    @Column(name = "recursosf", nullable = false)
    private Long recursosFinancieros; // Mismo campo que oros

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proyecto proyecto = (Proyecto) o;
        return id != null && id.equals(proyecto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
