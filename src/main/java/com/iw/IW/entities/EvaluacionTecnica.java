package com.iw.IW.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluacion_t")
public class EvaluacionTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idS", nullable = false)
    private Long idS;

    @OneToOne
    @JoinColumn(name = "idS", referencedColumnName = "id", insertable = false, updatable = false )
    private Solicitud solicitud;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDateTime fechaEvaluacion;

    @Column(nullable = true, length = 255)
    private String alineamiento;

    @Column(nullable = true, length = 255)
    private String recursosH;

    @Column(nullable = true)
    private Long recursosF;

    public EvaluacionTecnica() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getIdS() {
        return this.idS;
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

    public String getRecursosH() {
        return this.recursosH;
    }

    public Long getRecursosF() {
        return this.recursosF;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
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

    public void setRecursosH(String recursosH) {
        this.recursosH = recursosH;
    }

    public void setRecursosF(Long recursosF) {
        this.recursosF = recursosF;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EvaluacionTecnica)) return false;
        final EvaluacionTecnica other = (EvaluacionTecnica) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$idS = this.getIdS();
        final Object other$idS = other.getIdS();
        if (this$idS == null ? other$idS != null : !this$idS.equals(other$idS)) return false;
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
        final Object this$recursosH = this.getRecursosH();
        final Object other$recursosH = other.getRecursosH();
        if (this$recursosH == null ? other$recursosH != null : !this$recursosH.equals(other$recursosH)) return false;
        final Object this$recursosF = this.getRecursosF();
        final Object other$recursosF = other.getRecursosF();
        if (this$recursosF == null ? other$recursosF != null : !this$recursosF.equals(other$recursosF)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EvaluacionTecnica;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $idS = this.getIdS();
        result = result * PRIME + ($idS == null ? 43 : $idS.hashCode());
        final Object $solicitud = this.getSolicitud();
        result = result * PRIME + ($solicitud == null ? 43 : $solicitud.hashCode());
        final Object $descripcion = this.getDescripcion();
        result = result * PRIME + ($descripcion == null ? 43 : $descripcion.hashCode());
        final Object $fechaEvaluacion = this.getFechaEvaluacion();
        result = result * PRIME + ($fechaEvaluacion == null ? 43 : $fechaEvaluacion.hashCode());
        final Object $alineamiento = this.getAlineamiento();
        result = result * PRIME + ($alineamiento == null ? 43 : $alineamiento.hashCode());
        final Object $recursosH = this.getRecursosH();
        result = result * PRIME + ($recursosH == null ? 43 : $recursosH.hashCode());
        final Object $recursosF = this.getRecursosF();
        result = result * PRIME + ($recursosF == null ? 43 : $recursosF.hashCode());
        return result;
    }

    public String toString() {
        return "EvaluacionTecnica(id=" + this.getId() + ", idS=" + this.getIdS() + ", solicitud=" + this.getSolicitud() + ", descripcion=" + this.getDescripcion() + ", fechaEvaluacion=" + this.getFechaEvaluacion() + ", alineamiento=" + this.getAlineamiento() + ", recursosH=" + this.getRecursosH() + ", recursosF=" + this.getRecursosF() + ")";
    }
}
