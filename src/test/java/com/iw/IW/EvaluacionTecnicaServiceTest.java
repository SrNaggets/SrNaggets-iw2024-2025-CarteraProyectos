package com.iw.IW;

import com.iw.IW.entities.EvaluacionTecnica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.EvaluacionTecnicaRepository;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.EmailService;
import com.iw.IW.services.EvaluacionTecnicaService;
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
class EvaluacionTecnicaServiceTest {

    @MockBean
    private EvaluacionTecnicaRepository evaluacionTecnicaRepository;

    @MockBean
    private SolicitudRepository solicitudRepository;

    @MockBean
    private EmailService emailService;

    @MockBean
    private SolicitudService solicitudService;

    @Autowired
    private EvaluacionTecnicaService evaluacionTecnicaService;

    @Test
    void testRegistrarEvaluacionTecnica() {
        Long solicitudId = 1L;
        Solicitud solicitud = new Solicitud();
        solicitud.setId(solicitudId);
        solicitud.setTitulo("Proyecto A");
        solicitud.setUsuario(new com.iw.IW.entities.Usuario());
        solicitud.getUsuario().setCorreo("user@example.com");

        EvaluacionTecnica evaluacion = new EvaluacionTecnica();
        evaluacion.setDescripcion("Descripción técnica");
        evaluacion.setRecursosH("Recursos Humanos");
        evaluacion.setRecursosF(10000L);
        evaluacion.setAlineamiento("Alineamiento técnico");

        Mockito.when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.of(solicitud));
        Mockito.when(evaluacionTecnicaRepository.save(Mockito.any(EvaluacionTecnica.class))).thenReturn(evaluacion);

        EvaluacionTecnica resultado = evaluacionTecnicaService.registrarEvaluacionTecnica(solicitudId, evaluacion);

        assertNotNull(resultado);
        assertEquals("Descripción técnica", resultado.getDescripcion());
        Mockito.verify(emailService, Mockito.times(1)).enviarCorreoEvaluacionTecnica(
                "user@example.com",
                "Proyecto A",
                "Descripción técnica",
                "Recursos Humanos",
                10000L,
                "Alineamiento técnico"
        );
        Mockito.verify(solicitudService, Mockito.times(1))
                .cambiarEstado(solicitudId, "pendiente de evaluación estratégica", null);
    }
}

