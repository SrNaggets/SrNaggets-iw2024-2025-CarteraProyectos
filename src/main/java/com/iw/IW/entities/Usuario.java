package com.iw.IW.entities;

import com.fasterxml.jackson.annotation.JsonIgnore; // Asegúrate de que se importa correctamente
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
}
