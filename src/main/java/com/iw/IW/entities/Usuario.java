package com.iw.IW.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String role;

    @Column(nullable = false, unique = true, length = 255)
    private String correo;

    @Column(nullable = false, length = 40)
    @JsonIgnore
    private String contraseña;

    @Column(nullable = true, length = 10)
    private String verificacion;

    @Column(name = "tiempo_veri", nullable = true)
    private LocalDateTime tiempoVeri;

    @Column(nullable = false)
    private Integer veri = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Usuario() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Long getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getRole() {
        return this.role;
    }

    public String getCorreo() {
        return this.correo;
    }

    public String getContraseña() {
        return this.contraseña;
    }

    public String getVerificacion() {
        return this.verificacion;
    }

    public LocalDateTime getTiempoVeri() {
        return this.tiempoVeri;
    }

    public Integer getVeri() {
        return this.veri;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @JsonIgnore
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setVerificacion(String verificacion) {
        this.verificacion = verificacion;
    }

    public void setTiempoVeri(LocalDateTime tiempoVeri) {
        this.tiempoVeri = tiempoVeri;
    }

    public void setVeri(Integer veri) {
        this.veri = veri;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "Usuario(id=" + this.getId() + ", nombre=" + this.getNombre() + ", role=" + this.getRole() + ", correo=" + this.getCorreo() + ", contraseña=" + this.getContraseña() + ", verificacion=" + this.getVerificacion() + ", tiempoVeri=" + this.getTiempoVeri() + ", veri=" + this.getVeri() + ", createdAt=" + this.getCreatedAt() + ")";
    }
}
