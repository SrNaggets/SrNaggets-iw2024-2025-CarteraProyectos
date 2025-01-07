package com.iw.IW.security;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("solicitudSecurity")
public class SolicitudSecurity {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean puedeAcceder(Long solicitudId, Authentication authentication) {
        String emailActual = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(emailActual)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        return usuario.getRole().equals("CIO") || solicitud.getUsuario().getId().equals(usuario.getId());
    }
}
