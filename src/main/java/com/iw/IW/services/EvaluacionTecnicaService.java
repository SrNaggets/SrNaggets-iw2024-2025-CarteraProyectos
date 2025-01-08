package com.iw.IW.services;

import com.iw.IW.entities.EvaluacionTecnica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.EvaluacionTecnicaRepository;
import com.iw.IW.repositories.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EvaluacionTecnicaService {

    @Autowired
    private EvaluacionTecnicaRepository evaluacionTecnicaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudService solicitudService;

    @PreAuthorize("hasRole('OTP')")
    public EvaluacionTecnica registrarEvaluacionTecnica(Long solicitudId, EvaluacionTecnica evaluacion) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        evaluacion.setIdS(solicitudId);
        evaluacion.setFechaEvaluacion(LocalDateTime.now());

        EvaluacionTecnica evaluacionGuardada = evaluacionTecnicaRepository.save(evaluacion);

        String correoUsuario = solicitud.getUsuario().getCorreo();
        emailService.enviarCorreoEvaluacionTecnica(
                correoUsuario,
                solicitud.getTitulo(),
                evaluacion.getDescripcion(),
                evaluacion.getRecursosH(),
                evaluacion.getRecursosF(),
                evaluacion.getAlineamiento()
        );

        solicitudService.cambiarEstado(solicitudId, "pendiente de evaluación estratégica", null);

        return evaluacionGuardada;
    }

}
