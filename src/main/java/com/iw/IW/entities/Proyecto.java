package com.iw.IW.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    public Proyecto() {
    }

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

    public Long getId() {
        return this.id;
    }

    public Solicitud getSolicitud() {
        return this.solicitud;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getEstado() {
        return this.estado;
    }

    public Integer getRecursosHumanos() {
        return this.recursosHumanos;
    }

    public Long getRecursosFinancieros() {
        return this.recursosFinancieros;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setRecursosHumanos(Integer recursosHumanos) {
        this.recursosHumanos = recursosHumanos;
    }

    public void setRecursosFinancieros(Long recursosFinancieros) {
        this.recursosFinancieros = recursosFinancieros;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "Proyecto(id=" + this.getId() + ", solicitud=" + this.getSolicitud() + ", descripcion=" + this.getDescripcion() + ", estado=" + this.getEstado() + ", recursosHumanos=" + this.getRecursosHumanos() + ", recursosFinancieros=" + this.getRecursosFinancieros() + ", createdAt=" + this.getCreatedAt() + ")";
    }
}
