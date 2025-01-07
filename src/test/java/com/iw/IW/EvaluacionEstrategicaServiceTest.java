package com.iw.IW;


import com.iw.IW.entities.EvaluacionEstrategica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.EvaluacionEstrategicaRepository;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.EmailService;
import com.iw.IW.services.EvaluacionEstrategicaService;
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

public class EvaluacionEstrategicaServiceTest {

    @InjectMocks
    private EvaluacionEstrategicaService evaluacionEstrategicaService;

    @Mock
    private EvaluacionEstrategicaRepository evaluacionEstrategicaRepository;

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
    void registrarEvaluacionEstrategica_Success() {
        Long solicitudId = 1L;
        EvaluacionEstrategica evaluacion = new EvaluacionEstrategica();
        evaluacion.setDescripcion("Buena alineación");
        evaluacion.setAlineamiento("Alta");

        Solicitud solicitudMock = new Solicitud();
        solicitudMock.setId(solicitudId);
        solicitudMock.setTitulo("Proyecto X");
        solicitudMock.setUsuario(new Usuario());
        solicitudMock.getUsuario().setCorreo("usuario@correo.com");

        when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.of(solicitudMock));
        when(evaluacionEstrategicaRepository.save(any(EvaluacionEstrategica.class))).thenReturn(evaluacion);

        EvaluacionEstrategica resultado = evaluacionEstrategicaService.registrarEvaluacionEstrategica(solicitudId, evaluacion);

        assertNotNull(resultado);
        assertEquals("Buena alineación", resultado.getDescripcion());
        verify(emailService).enviarCorreoEvaluacionEstrategica(
                "usuario@correo.com", "Proyecto X", "Buena alineación", "Alta"
        );
        verify(solicitudService).cambiarEstado(solicitudId, "evaluado", null);
    }

    @Test
    void registrarEvaluacionEstrategica_SolicitudNoEncontrada() {
        Long solicitudId = 1L;
        EvaluacionEstrategica evaluacion = new EvaluacionEstrategica();

        when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                evaluacionEstrategicaService.registrarEvaluacionEstrategica(solicitudId, evaluacion)
        );

        assertEquals("Solicitud no encontrada", exception.getMessage());
    }
}

