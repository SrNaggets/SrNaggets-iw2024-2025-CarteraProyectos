package com.iw.IW.services;

import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
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
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(correo);
        if (usuarioExistente.isPresent()) {
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
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        if (usuario.getTiempoVeri().plusHours(EXPIRACION_HORAS).isBefore(LocalDateTime.now())) {
            usuarioRepository.delete(usuario);
            throw new RuntimeException("El código ha expirado. Regístrate de nuevo.");
        }


        if (!usuario.getVerificacion().equals(codigo)) {
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
            throw new RuntimeException("Usuario no verificado");
        }

        if (!passwordEncoder.matches(contraseña, usuario.getContraseña())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }
    public void reiniciarContraseña(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String nuevaContraseña = generarCodigoAleatorio();
        usuario.setContraseña(passwordEncoder.encode(nuevaContraseña));

        usuarioRepository.save(usuario);

        emailService.enviarCorreoRecuperacionContraseña(correo, nuevaContraseña);
    }

    public Usuario modificarUsuario(Long id, String nuevoNombre, String nuevaContraseña) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            usuario.setNombre(nuevoNombre);
        }

        if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
            usuario.setContraseña(passwordEncoder.encode(nuevaContraseña));
        }

        return usuarioRepository.save(usuario);
    }

    private String generarCodigoAleatorio() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }

    public Usuario cambiarRolUsuario(String correoUsuario, String nuevoRol, Long idSolicitante) {
        Usuario solicitante = usuarioRepository.findById(idSolicitante)
                .orElseThrow(() -> new RuntimeException("Solicitante no encontrado"));

        if (!"CIO".equals(solicitante.getRole())) {
            throw new RuntimeException("No tienes permisos para cambiar roles");
        }

        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!List.of("CIO", "OTP", "PROMOTOR", "normal").contains(nuevoRol)) {
            throw new RuntimeException("Rol inválido");
        }

        usuario.setRole(nuevoRol);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        emailService.enviarCorreoCambioRol(usuario.getCorreo(), nuevoRol);

        return usuarioActualizado;
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
