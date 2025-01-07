package com.iw.IW;


import com.iw.IW.entities.EvaluacionTecnica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.EvaluacionTecnicaRepository;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.EmailService;
import com.iw.IW.services.EvaluacionTecnicaService;
import com.iw.IW.services.SolicitudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EvaluacionTecnicaServiceTest {

    @InjectMocks
    private EvaluacionTecnicaService evaluacionTecnicaService;

    @Mock
    private EvaluacionTecnicaRepository evaluacionTecnicaRepository;

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private SolicitudService solicitudService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarEvaluacionTecnica_Success() {
        Long solicitudId = 1L;
        EvaluacionTecnica evaluacion = new EvaluacionTecnica();
        evaluacion.setDescripcion("Evaluación técnica detallada");
        evaluacion.setRecursosH("2 ingenieros");
        evaluacion.setRecursosF(5000L);
        evaluacion.setAlineamiento("Moderado");

        Solicitud solicitudMock = new Solicitud();
        solicitudMock.setId(solicitudId);
        solicitudMock.setTitulo("Proyecto Y");
        solicitudMock.setUsuario(new Usuario());
        solicitudMock.getUsuario().setCorreo("usuario@correo.com");

        when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.of(solicitudMock));
        when(evaluacionTecnicaRepository.save(any(EvaluacionTecnica.class))).thenReturn(evaluacion);

        EvaluacionTecnica resultado = evaluacionTecnicaService.registrarEvaluacionTecnica(solicitudId, evaluacion);

        assertNotNull(resultado);
        assertEquals("Evaluación técnica detallada", resultado.getDescripcion());
        verify(emailService).enviarCorreoEvaluacionTecnica(
                "usuario@correo.com", "Proyecto Y", "Evaluación técnica detallada", "2 ingenieros", 5000L, "Moderado"
        );
        verify(solicitudService).cambiarEstado(solicitudId, "pendiente de evaluación estratégica", null);
    }

    @Test
    void registrarEvaluacionTecnica_SolicitudNoEncontrada() {
        Long solicitudId = 1L;
        EvaluacionTecnica evaluacion = new EvaluacionTecnica();

        when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                evaluacionTecnicaService.registrarEvaluacionTecnica(solicitudId, evaluacion)
        );

        assertEquals("Solicitud no encontrada", exception.getMessage());
    }
}

