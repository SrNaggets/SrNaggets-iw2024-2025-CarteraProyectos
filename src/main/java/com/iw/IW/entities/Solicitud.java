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

    @Lob
    @Column(nullable = false)
    private byte[] memoria;

    @Lob
    @Column(nullable = false)
    private byte[] tecnico;

    @Lob
    @Column(nullable = false)
    private byte[] presupuesto;

    @Column(nullable = false, length = 40)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_u", nullable = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_p", nullable = true)
    private Usuario promotor;

    @Column(nullable = false, length = 256)
    private String interesados;

    @Column(nullable = true)
    private Long oros;

    @Column(nullable = false)
    private Integer ali1;

    @Column(nullable = false)
    private Integer ali2;

    @Column(nullable = false)
    private Integer ali3;

    @Column(nullable = false)
    private Integer ali4;

    @Column(nullable = false)
    private Integer ali5;

    @Column(nullable = false)
    private Integer ali6;

    @Column(nullable = false)
    private Integer ali7;

    @Column(nullable = false, length = 200)
    private String alcance;

    @Column(name = "importancia_p", nullable = true)
    private Integer importanciaPromotor;

    @Column(nullable = false, length = 255)
    private String estado = "solicitado";

    @Column(nullable = false)
    private Integer prioridad;

    @Column(name = "fecha_max", nullable = true)
    private LocalDateTime fechaMaxima;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Integer getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Integer prioridad) {
        if (prioridad < 1 || prioridad > 5) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 5");
        }
        this.prioridad = prioridad;
    }
}
