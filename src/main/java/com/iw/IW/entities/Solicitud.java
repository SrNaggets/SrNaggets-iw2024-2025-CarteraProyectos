package com.iw.IW.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    public Solicitud() {
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        if (prioridad < 1 || prioridad > 5) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 5");
        }
        this.prioridad = prioridad;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public byte[] getMemoria() {
        return this.memoria;
    }

    public byte[] getTecnico() {
        return this.tecnico;
    }

    public byte[] getPresupuesto() {
        return this.presupuesto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public Usuario getPromotor() {
        return this.promotor;
    }

    public String getInteresados() {
        return this.interesados;
    }

    public Long getOros() {
        return this.oros;
    }

    public Integer getAli1() {
        return this.ali1;
    }

    public Integer getAli2() {
        return this.ali2;
    }

    public Integer getAli3() {
        return this.ali3;
    }

    public Integer getAli4() {
        return this.ali4;
    }

    public Integer getAli5() {
        return this.ali5;
    }

    public Integer getAli6() {
        return this.ali6;
    }

    public Integer getAli7() {
        return this.ali7;
    }

    public String getAlcance() {
        return this.alcance;
    }

    public Integer getImportanciaPromotor() {
        return this.importanciaPromotor;
    }

    public String getEstado() {
        return this.estado;
    }

    public LocalDateTime getFechaMaxima() {
        return this.fechaMaxima;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMemoria(byte[] memoria) {
        this.memoria = memoria;
    }

    public void setTecnico(byte[] tecnico) {
        this.tecnico = tecnico;
    }

    public void setPresupuesto(byte[] presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setPromotor(Usuario promotor) {
        this.promotor = promotor;
    }

    public void setInteresados(String interesados) {
        this.interesados = interesados;
    }

    public void setOros(Long oros) {
        this.oros = oros;
    }

    public void setAli1(Integer ali1) {
        this.ali1 = ali1;
    }

    public void setAli2(Integer ali2) {
        this.ali2 = ali2;
    }

    public void setAli3(Integer ali3) {
        this.ali3 = ali3;
    }

    public void setAli4(Integer ali4) {
        this.ali4 = ali4;
    }

    public void setAli5(Integer ali5) {
        this.ali5 = ali5;
    }

    public void setAli6(Integer ali6) {
        this.ali6 = ali6;
    }

    public void setAli7(Integer ali7) {
        this.ali7 = ali7;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public void setImportanciaPromotor(Integer importanciaPromotor) {
        this.importanciaPromotor = importanciaPromotor;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaMaxima(LocalDateTime fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Solicitud)) return false;
        final Solicitud other = (Solicitud) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$titulo = this.getTitulo();
        final Object other$titulo = other.getTitulo();
        if (this$titulo == null ? other$titulo != null : !this$titulo.equals(other$titulo)) return false;
        if (!java.util.Arrays.equals(this.getMemoria(), other.getMemoria())) return false;
        if (!java.util.Arrays.equals(this.getTecnico(), other.getTecnico())) return false;
        if (!java.util.Arrays.equals(this.getPresupuesto(), other.getPresupuesto())) return false;
        final Object this$nombre = this.getNombre();
        final Object other$nombre = other.getNombre();
        if (this$nombre == null ? other$nombre != null : !this$nombre.equals(other$nombre)) return false;
        final Object this$usuario = this.getUsuario();
        final Object other$usuario = other.getUsuario();
        if (this$usuario == null ? other$usuario != null : !this$usuario.equals(other$usuario)) return false;
        final Object this$promotor = this.getPromotor();
        final Object other$promotor = other.getPromotor();
        if (this$promotor == null ? other$promotor != null : !this$promotor.equals(other$promotor)) return false;
        final Object this$interesados = this.getInteresados();
        final Object other$interesados = other.getInteresados();
        if (this$interesados == null ? other$interesados != null : !this$interesados.equals(other$interesados))
            return false;
        final Object this$oros = this.getOros();
        final Object other$oros = other.getOros();
        if (this$oros == null ? other$oros != null : !this$oros.equals(other$oros)) return false;
        final Object this$ali1 = this.getAli1();
        final Object other$ali1 = other.getAli1();
        if (this$ali1 == null ? other$ali1 != null : !this$ali1.equals(other$ali1)) return false;
        final Object this$ali2 = this.getAli2();
        final Object other$ali2 = other.getAli2();
        if (this$ali2 == null ? other$ali2 != null : !this$ali2.equals(other$ali2)) return false;
        final Object this$ali3 = this.getAli3();
        final Object other$ali3 = other.getAli3();
        if (this$ali3 == null ? other$ali3 != null : !this$ali3.equals(other$ali3)) return false;
        final Object this$ali4 = this.getAli4();
        final Object other$ali4 = other.getAli4();
        if (this$ali4 == null ? other$ali4 != null : !this$ali4.equals(other$ali4)) return false;
        final Object this$ali5 = this.getAli5();
        final Object other$ali5 = other.getAli5();
        if (this$ali5 == null ? other$ali5 != null : !this$ali5.equals(other$ali5)) return false;
        final Object this$ali6 = this.getAli6();
        final Object other$ali6 = other.getAli6();
        if (this$ali6 == null ? other$ali6 != null : !this$ali6.equals(other$ali6)) return false;
        final Object this$ali7 = this.getAli7();
        final Object other$ali7 = other.getAli7();
        if (this$ali7 == null ? other$ali7 != null : !this$ali7.equals(other$ali7)) return false;
        final Object this$alcance = this.getAlcance();
        final Object other$alcance = other.getAlcance();
        if (this$alcance == null ? other$alcance != null : !this$alcance.equals(other$alcance)) return false;
        final Object this$importanciaPromotor = this.getImportanciaPromotor();
        final Object other$importanciaPromotor = other.getImportanciaPromotor();
        if (this$importanciaPromotor == null ? other$importanciaPromotor != null : !this$importanciaPromotor.equals(other$importanciaPromotor))
            return false;
        final Object this$estado = this.getEstado();
        final Object other$estado = other.getEstado();
        if (this$estado == null ? other$estado != null : !this$estado.equals(other$estado)) return false;
        final Object this$prioridad = this.getPrioridad();
        final Object other$prioridad = other.getPrioridad();
        if (this$prioridad == null ? other$prioridad != null : !this$prioridad.equals(other$prioridad)) return false;
        final Object this$fechaMaxima = this.getFechaMaxima();
        final Object other$fechaMaxima = other.getFechaMaxima();
        if (this$fechaMaxima == null ? other$fechaMaxima != null : !this$fechaMaxima.equals(other$fechaMaxima))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Solicitud;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $titulo = this.getTitulo();
        result = result * PRIME + ($titulo == null ? 43 : $titulo.hashCode());
        result = result * PRIME + java.util.Arrays.hashCode(this.getMemoria());
        result = result * PRIME + java.util.Arrays.hashCode(this.getTecnico());
        result = result * PRIME + java.util.Arrays.hashCode(this.getPresupuesto());
        final Object $nombre = this.getNombre();
        result = result * PRIME + ($nombre == null ? 43 : $nombre.hashCode());
        final Object $usuario = this.getUsuario();
        result = result * PRIME + ($usuario == null ? 43 : $usuario.hashCode());
        final Object $promotor = this.getPromotor();
        result = result * PRIME + ($promotor == null ? 43 : $promotor.hashCode());
        final Object $interesados = this.getInteresados();
        result = result * PRIME + ($interesados == null ? 43 : $interesados.hashCode());
        final Object $oros = this.getOros();
        result = result * PRIME + ($oros == null ? 43 : $oros.hashCode());
        final Object $ali1 = this.getAli1();
        result = result * PRIME + ($ali1 == null ? 43 : $ali1.hashCode());
        final Object $ali2 = this.getAli2();
        result = result * PRIME + ($ali2 == null ? 43 : $ali2.hashCode());
        final Object $ali3 = this.getAli3();
        result = result * PRIME + ($ali3 == null ? 43 : $ali3.hashCode());
        final Object $ali4 = this.getAli4();
        result = result * PRIME + ($ali4 == null ? 43 : $ali4.hashCode());
        final Object $ali5 = this.getAli5();
        result = result * PRIME + ($ali5 == null ? 43 : $ali5.hashCode());
        final Object $ali6 = this.getAli6();
        result = result * PRIME + ($ali6 == null ? 43 : $ali6.hashCode());
        final Object $ali7 = this.getAli7();
        result = result * PRIME + ($ali7 == null ? 43 : $ali7.hashCode());
        final Object $alcance = this.getAlcance();
        result = result * PRIME + ($alcance == null ? 43 : $alcance.hashCode());
        final Object $importanciaPromotor = this.getImportanciaPromotor();
        result = result * PRIME + ($importanciaPromotor == null ? 43 : $importanciaPromotor.hashCode());
        final Object $estado = this.getEstado();
        result = result * PRIME + ($estado == null ? 43 : $estado.hashCode());
        final Object $prioridad = this.getPrioridad();
        result = result * PRIME + ($prioridad == null ? 43 : $prioridad.hashCode());
        final Object $fechaMaxima = this.getFechaMaxima();
        result = result * PRIME + ($fechaMaxima == null ? 43 : $fechaMaxima.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "Solicitud(id=" + this.getId() + ", titulo=" + this.getTitulo() + ", memoria=" + java.util.Arrays.toString(this.getMemoria()) + ", tecnico=" + java.util.Arrays.toString(this.getTecnico()) + ", presupuesto=" + java.util.Arrays.toString(this.getPresupuesto()) + ", nombre=" + this.getNombre() + ", usuario=" + this.getUsuario() + ", promotor=" + this.getPromotor() + ", interesados=" + this.getInteresados() + ", oros=" + this.getOros() + ", ali1=" + this.getAli1() + ", ali2=" + this.getAli2() + ", ali3=" + this.getAli3() + ", ali4=" + this.getAli4() + ", ali5=" + this.getAli5() + ", ali6=" + this.getAli6() + ", ali7=" + this.getAli7() + ", alcance=" + this.getAlcance() + ", importanciaPromotor=" + this.getImportanciaPromotor() + ", estado=" + this.getEstado() + ", prioridad=" + this.getPrioridad() + ", fechaMaxima=" + this.getFechaMaxima() + ", createdAt=" + this.getCreatedAt() + ")";
    }
}
