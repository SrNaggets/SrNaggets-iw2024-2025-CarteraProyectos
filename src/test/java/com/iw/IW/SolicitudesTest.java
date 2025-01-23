package com.iw.IW;


import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.EmailService;
import com.iw.IW.services.SolicitudService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SolicitudServiceTest {

    @MockBean
    private SolicitudRepository solicitudRepository;

    @MockBean
    private EmailService emailService;

    @Autowired
    private SolicitudService solicitudService;

    @Test
    void testCrearSolicitud() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("usuario@ejemplo.com");

        Solicitud nuevaSolicitud = new Solicitud();
        nuevaSolicitud.setTitulo("Nueva Solicitud");
        nuevaSolicitud.setUsuario(usuario);

        Mockito.when(solicitudRepository.save(Mockito.any(Solicitud.class))).thenReturn(nuevaSolicitud);

        Solicitud resultado = solicitudService.crearSolicitud(
                "Nueva Solicitud", "Nombre", "Interesados", 100L, 1, 0, 0, 0, 0, 0, 0, "Alcance",
                5, usuario, null, null, null, 3
        );

        assertNotNull(resultado);
        assertEquals("Nueva Solicitud", resultado.getTitulo());
        Mockito.verify(emailService, Mockito.times(1)).enviarCorreoCambioEstado(
                usuario.getCorreo(), "Nueva Solicitud", "solicitado", "Tu solicitud ha sido registrada correctamente."
        );
    }

    @Test
    void testCambiarEstado() {

        Long solicitudId = 1L;
        String nuevoEstado = "evaluado";
        Solicitud solicitud = new Solicitud();
        solicitud.setId(solicitudId);
        solicitud.setEstado("pendiente");

        Mockito.when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.of(solicitud));
        Mockito.when(solicitudRepository.save(Mockito.any(Solicitud.class))).thenReturn(solicitud);

        Solicitud resultado = solicitudService.cambiarEstado(solicitudId, nuevoEstado, "Estado actualizado");

        assertNotNull(resultado);
        assertEquals(nuevoEstado, resultado.getEstado());
        Mockito.verify(emailService, Mockito.times(1)).enviarCorreoCambioEstado(
                solicitud.getUsuario().getCorreo(), solicitud.getTitulo(), nuevoEstado, "Estado actualizado"
        );
    }


}

