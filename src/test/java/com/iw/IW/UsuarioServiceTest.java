package com.iw.IW;


import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.EmailService;
import com.iw.IW.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioServiceTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService;

    @Test
    void testRegistrarUsuario() {
        String correo = "usuario@ejemplo.com";
        String nombre = "Usuario Prueba";
        String contraseña = "password123";

        Mockito.when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(contraseña)).thenReturn("encodedPassword");
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setContraseña("encodedPassword");
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(nuevoUsuario);

        Usuario resultado = usuarioService.registrarUsuario(correo, nombre, contraseña);

        assertNotNull(resultado);
        assertEquals(correo, resultado.getCorreo());
        assertEquals(nombre, resultado.getNombre());
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
        Mockito.verify(emailService, Mockito.times(1)).enviarCorreoVerificacion(Mockito.eq(correo), Mockito.anyString());
    }

    @Test
    void testCambiarRol() {
        String correo = "usuario@ejemplo.com";
        String nuevoRol = "CIO";
        Long idSolicitante = 1L;

        Usuario solicitante = new Usuario();
        solicitante.setId(idSolicitante);
        solicitante.setRole("CIO");

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setRole("normal");

        Mockito.when(usuarioRepository.findById(idSolicitante)).thenReturn(Optional.of(solicitante));
        Mockito.when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.cambiarRolUsuario(correo, nuevoRol, idSolicitante);

        assertNotNull(resultado);
        assertEquals(nuevoRol, resultado.getRole());
        Mockito.verify(emailService, Mockito.times(1)).enviarCorreoCambioRol(correo, nuevoRol);
    }


    @Test
    void testReiniciarContraseña() {
        String correo = "usuario@ejemplo.com";
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContraseña("oldPassword");

        Mockito.when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        usuarioService.reiniciarContraseña(correo);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
        Mockito.verify(emailService, Mockito.times(1)).enviarCorreoRecuperacionContraseña(Mockito.eq(correo), Mockito.anyString());
    }

}

