package com.iw.IW.services;

import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final int EXPIRACION_HORAS = 1;

    public Usuario registrarUsuario(String correo, String nombre, String contraseña) {
        Optional<Usuario> usuarioExistente2 = usuarioRepository.findByNombreOptional(correo);
        if (usuarioExistente2.isPresent()) {
            Notification.show("Ese nombre de usuario ya está registrado", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new RuntimeException("Ese nombre de usuario ya está registrado");
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(correo);
        if (usuarioExistente.isPresent()) {
            Notification.show("El correo ya está registrado", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new RuntimeException("El correo ya está registrado");
        }


        String codigoVerificacion = generarCodigoVerificacion();


        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setContraseña(passwordEncoder.encode(contraseña));
        nuevoUsuario.setRole("normal");
        nuevoUsuario.setVerificacion(codigoVerificacion);
        nuevoUsuario.setTiempoVeri(LocalDateTime.now());
        nuevoUsuario.setVeri(0);
        usuarioRepository.save(nuevoUsuario);


        emailService.enviarCorreoVerificacion(correo, codigoVerificacion);

        return nuevoUsuario;
    }
    public void reenviarCorreo(String correo, String codigo){
        emailService.enviarCorreoVerificacion(correo, codigo);
    }

    public void verificarUsuario(String correo, String codigo) {

        codigo = codigo.trim();

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        if (usuario.getTiempoVeri().plusHours(EXPIRACION_HORAS).isBefore(LocalDateTime.now())) {
            usuarioRepository.delete(usuario);
            Notification.show("El código ha expirado. Regístrate de nuevo.", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new RuntimeException("El código ha expirado. Regístrate de nuevo.");
        }


        if (!usuario.getVerificacion().equals(codigo)) {
            Notification.show("Código de verificación incorrecto", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new RuntimeException("Código de verificación incorrecto");
        }


        usuario.setVeri(1);
        usuario.setVerificacion(null);
        usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String correo, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getVeri() == 0) {
            Notification.show("Usuario no verificado", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new RuntimeException("Usuario no verificado");
        }

        if (!passwordEncoder.matches(contraseña, usuario.getContraseña())) {
            Notification.show("Contraseña incorrecta", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }


    public List<Usuario> buscarPromotoresPorNombre(String nombre) {
        return usuarioRepository.findByRoleAndNombreContainingIgnoreCase("PROMOTOR", nombre);
    }

    public List<Usuario> buscarPromotoresPorCorreo(String correo) {
        return usuarioRepository.findByRoleAndCorreoContainingIgnoreCase("PROMOTOR", correo);
    }

    public Usuario buscarPromotorPorId(Long id) {
        return usuarioRepository.findByIdAndRole(id, "PROMOTOR")
                .orElseThrow(() -> new RuntimeException("Promotor no encontrado"));
    }


    private String generarCodigoVerificacion() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }
}
