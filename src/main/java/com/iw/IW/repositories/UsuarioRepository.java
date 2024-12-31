package com.iw.IW.repositories;

import com.iw.IW.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByRoleAndNombreContainingIgnoreCase(String role, String nombre);
    List<Usuario> findByRoleAndCorreoContainingIgnoreCase(String role, String correo);
    Optional<Usuario> findByIdAndRole(Long id, String role);
    Optional<Usuario> findByCorreo(String correo);
}
