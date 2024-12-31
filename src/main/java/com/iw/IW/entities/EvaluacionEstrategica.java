package com.iw.IW.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "evaluacion_e")
public class EvaluacionEstrategica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Solicitud solicitud;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDateTime fechaEvaluacion;

    @Column(nullable = true, length = 255)
    private String alineamiento;
}
