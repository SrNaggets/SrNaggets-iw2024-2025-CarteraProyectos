package com.iw.IW.services;

import com.iw.IW.entities.EvaluacionEstrategica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.EvaluacionEstrategicaRepository;
import com.iw.IW.repositories.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import java.time.LocalDateTime;

@Service
public class EvaluacionEstrategicaService {

    @Autowired
    private EvaluacionEstrategicaRepository evaluacionEstrategicaRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SolicitudService solicitudService;

    @PreAuthorize("hasRole('CIO')")
    public EvaluacionEstrategica registrarEvaluacionEstrategica(Long solicitudId, EvaluacionEstrategica evaluacion) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        evaluacion.setSolicitud(solicitud);
        evaluacion.setFechaEvaluacion(LocalDateTime.now());
        EvaluacionEstrategica evaluacionGuardada = evaluacionEstrategicaRepository.save(evaluacion);


        String correoUsuario = solicitud.getUsuario().getCorreo();
        emailService.enviarCorreoEvaluacionEstrategica(
                correoUsuario,
                solicitud.getTitulo(),
                evaluacion.getDescripcion(),
                evaluacion.getAlineamiento()
        );

        solicitudService.cambiarEstado(solicitudId, "evaluado", null);
        return evaluacionGuardada;
    }

}
