package com.iw.IW.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    public EvaluacionEstrategica() {
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

    public LocalDateTime getFechaEvaluacion() {
        return this.fechaEvaluacion;
    }

    public String getAlineamiento() {
        return this.alineamiento;
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

    public void setFechaEvaluacion(LocalDateTime fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public void setAlineamiento(String alineamiento) {
        this.alineamiento = alineamiento;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EvaluacionEstrategica)) return false;
        final EvaluacionEstrategica other = (EvaluacionEstrategica) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$solicitud = this.getSolicitud();
        final Object other$solicitud = other.getSolicitud();
        if (this$solicitud == null ? other$solicitud != null : !this$solicitud.equals(other$solicitud)) return false;
        final Object this$descripcion = this.getDescripcion();
        final Object other$descripcion = other.getDescripcion();
        if (this$descripcion == null ? other$descripcion != null : !this$descripcion.equals(other$descripcion))
            return false;
        final Object this$fechaEvaluacion = this.getFechaEvaluacion();
        final Object other$fechaEvaluacion = other.getFechaEvaluacion();
        if (this$fechaEvaluacion == null ? other$fechaEvaluacion != null : !this$fechaEvaluacion.equals(other$fechaEvaluacion))
            return false;
        final Object this$alineamiento = this.getAlineamiento();
        final Object other$alineamiento = other.getAlineamiento();
        if (this$alineamiento == null ? other$alineamiento != null : !this$alineamiento.equals(other$alineamiento))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EvaluacionEstrategica;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $solicitud = this.getSolicitud();
        result = result * PRIME + ($solicitud == null ? 43 : $solicitud.hashCode());
        final Object $descripcion = this.getDescripcion();
        result = result * PRIME + ($descripcion == null ? 43 : $descripcion.hashCode());
        final Object $fechaEvaluacion = this.getFechaEvaluacion();
        result = result * PRIME + ($fechaEvaluacion == null ? 43 : $fechaEvaluacion.hashCode());
        final Object $alineamiento = this.getAlineamiento();
        result = result * PRIME + ($alineamiento == null ? 43 : $alineamiento.hashCode());
        return result;
    }

    public String toString() {
        return "EvaluacionEstrategica(id=" + this.getId() + ", solicitud=" + this.getSolicitud() + ", descripcion=" + this.getDescripcion() + ", fechaEvaluacion=" + this.getFechaEvaluacion() + ", alineamiento=" + this.getAlineamiento() + ")";
    }
}
