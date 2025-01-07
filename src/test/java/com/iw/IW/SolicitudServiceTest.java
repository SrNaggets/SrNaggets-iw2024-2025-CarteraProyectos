package com.iw.IW;


import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SolicitudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudServiceTest {

    @InjectMocks
    private SolicitudService solicitudService;

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario mockUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUsuario = new Usuario();
        mockUsuario.setId(1L);
        mockUsuario.setCorreo("test@example.com");
        mockUsuario.setNombre("Test User");
    }

    @Test
    void testCrearSolicitud() {
        Solicitud solicitud = new Solicitud();
        solicitud.setTitulo("Proyecto Test");
        solicitud.setNombre("Nombre Test");
        solicitud.setOros(500L);
        solicitud.setUsuario(mockUsuario);

        when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitud);

        Solicitud createdSolicitud = solicitudService.crearSolicitud(
                "Proyecto Test", "Nombre Test", "Interesados Test", 500L, 1, 0, 1, 0, 0, 0, 1,
                "Alcance Test", 5, mockUsuario, null, null, null, 3);

        assertNotNull(createdSolicitud);
        assertEquals("Proyecto Test", createdSolicitud.getTitulo());
        assertEquals("Nombre Test", createdSolicitud.getNombre());
        assertEquals(500L, createdSolicitud.getOros());
        verify(solicitudRepository, times(1)).save(any(Solicitud.class));
    }
}

