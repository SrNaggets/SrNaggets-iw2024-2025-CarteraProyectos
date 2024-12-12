package com.iw.IW.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "evaluacion_t")
public class EvaluacionTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Solicitud solicitud;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDateTime fechaEvaluacion;

    @Column(nullable = false, length = 255)
    private String alineamiento;

    @Column(name = "recursosh", nullable = false, length = 255)
    private String recursosHumanos;

    @Column(name = "recursosf", nullable = false)
    private Long recursosFinancieros;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluacionTecnica that = (EvaluacionTecnica) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
