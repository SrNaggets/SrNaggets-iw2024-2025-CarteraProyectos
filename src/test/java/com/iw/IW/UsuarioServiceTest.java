package com.iw.IW;


import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.iw.IW.services.UsuarioService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarUsuario_Success() {

        String correo = "test@example.com";
        String nombre = "Test User";
        String contraseña = "password123";

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(contraseña)).thenReturn("encodedPassword");

        Usuario usuarioRegistrado = usuarioService.registrarUsuario(correo, nombre, contraseña);

        assertNotNull(usuarioRegistrado);
        assertEquals(correo, usuarioRegistrado.getCorreo());
        assertEquals(nombre, usuarioRegistrado.getNombre());
        verify(emailService).enviarCorreoVerificacion(eq(correo), anyString());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testRegistrarUsuario_EmailYaRegistrado() {
        String correo = "test@example.com";
        String nombre = "Test User";
        String contraseña = "password123";

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(new Usuario()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuario(correo, nombre, contraseña);
        });
        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testVerificarUsuario_Success() {
        String correo = "test@example.com";
        String codigo = "123456";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setVerificacion(codigo);
        usuario.setTiempoVeri(LocalDateTime.now().minusMinutes(30));
        usuario.setVeri(0);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        usuarioService.verificarUsuario(correo, codigo);

        assertEquals(1, usuario.getVeri());
        assertNull(usuario.getVerificacion());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testVerificarUsuario_CodigoExpirado() {
        // Configuración
        String correo = "test@example.com";
        String codigo = "123456";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setVerificacion(codigo);
        usuario.setTiempoVeri(LocalDateTime.now().minusHours(2));
        usuario.setVeri(0);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.verificarUsuario(correo, codigo);
        });
        assertEquals("El código ha expirado. Regístrate de nuevo.", exception.getMessage());
        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void testAutenticarUsuario_Success() {

        String correo = "test@example.com";
        String contraseña = "password123";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContraseña("encodedPassword");
        usuario.setVeri(1);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(contraseña, "encodedPassword")).thenReturn(true);

        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(correo, contraseña);

        assertNotNull(usuarioAutenticado);
        assertEquals(correo, usuarioAutenticado.getCorreo());
    }

    @Test
    void testAutenticarUsuario_NoVerificado() {

        String correo = "test@example.com";
        String contraseña = "password123";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContraseña("encodedPassword");
        usuario.setVeri(0);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.autenticarUsuario(correo, contraseña);
        });
        assertEquals("Usuario no verificado", exception.getMessage());
    }
}

