package com.iw.IW;


import com.iw.IW.entities.EvaluacionEstrategica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.EvaluacionEstrategicaRepository;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.EmailService;
import com.iw.IW.services.EvaluacionEstrategicaService;
import com.iw.IW.services.SolicitudService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EvaluacionEstrategicaServiceTest {

    @MockBean
    private EvaluacionEstrategicaRepository evaluacionEstrategicaRepository;

    @MockBean
    private SolicitudRepository solicitudRepository;

    @MockBean
    private EmailService emailService;

    @MockBean
    private SolicitudService solicitudService;

    @Autowired
    private EvaluacionEstrategicaService evaluacionEstrategicaService;

    @Test
    void testRegistrarEvaluacionEstrategica() {
        Long solicitudId = 1L;
        Solicitud solicitud = new Solicitud();
        solicitud.setId(solicitudId);
        solicitud.setTitulo("Proyecto B");
        solicitud.setUsuario(new com.iw.IW.entities.Usuario());
        solicitud.getUsuario().setCorreo("user@example.com");

        EvaluacionEstrategica evaluacion = new EvaluacionEstrategica();
        evaluacion.setDescripcion("Descripción estratégica");
        evaluacion.setAlineamiento("Alineamiento estratégico");

        Mockito.when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.of(solicitud));
        Mockito.when(evaluacionEstrategicaRepository.save(Mockito.any(EvaluacionEstrategica.class))).thenReturn(evaluacion);

        EvaluacionEstrategica resultado = evaluacionEstrategicaService.registrarEvaluacionEstrategica(solicitudId, evaluacion);

        assertNotNull(resultado);
        assertEquals("Descripción estratégica", resultado.getDescripcion());
        Mockito.verify(emailService, Mockito.times(1)).enviarCorreoEvaluacionEstrategica(
                "user@example.com",
                "Proyecto B",
                "Descripción estratégica",
                "Alineamiento estratégico"
        );
        Mockito.verify(solicitudService, Mockito.times(1))
                .cambiarEstado(solicitudId, "evaluado", null);
    }
}

