package com.iw.IW.services;

import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(String correo, String nombre, String contraseña) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(correo);
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setRole("normal");
        nuevoUsuario.setContraseña(passwordEncoder.encode(contraseña));
        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario autenticarUsuario(String correo, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(contraseña, usuario.getContraseña())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }
}
