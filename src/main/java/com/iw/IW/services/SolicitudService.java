package com.iw.IW.services;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.repositories.UsuarioRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public Solicitud crearSolicitud(
            String titulo, String nombre, String interesados, Long oros, Integer ali1, Integer ali2, Integer ali3,
            Integer ali4, Integer ali5, Integer ali6, Integer ali7, String alcance, Integer importanciaPromotor,
            Usuario usuario, byte[] memoria, byte[] tecnico, byte[] presupuesto, Integer prioridad) {

        Solicitud solicitud = new Solicitud();
        solicitud.setTitulo(titulo);
        solicitud.setNombre(nombre);
        solicitud.setInteresados(interesados);
        solicitud.setOros(oros);
        solicitud.setAli1(ali1);
        solicitud.setAli2(ali2);
        solicitud.setAli3(ali3);
        solicitud.setAli4(ali4);
        solicitud.setAli5(ali5);
        solicitud.setAli6(ali6);
        solicitud.setAli7(ali7);
        solicitud.setAlcance(alcance);
        solicitud.setImportanciaPromotor(importanciaPromotor);
        solicitud.setUsuario(usuario);
        solicitud.setMemoria(memoria);
        solicitud.setTecnico(tecnico);
        solicitud.setPresupuesto(presupuesto);
        solicitud.setEstado("solicitado");
        solicitud.setCreatedAt(LocalDateTime.now());
        solicitud.setPrioridad(prioridad);

        Solicitud nuevaSolicitud = solicitudRepository.save(solicitud);


        emailService.enviarCorreoCambioEstado(
                usuario.getCorreo(),
                titulo,
                "solicitado",
                "Tu solicitud ha sido registrada correctamente."
        );

        return nuevaSolicitud;
    }

    public Solicitud cambiarEstado(Long solicitudId, String nuevoEstado, String mensajeAdicional) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(nuevoEstado);
        solicitudRepository.save(solicitud);


        String correoUsuario = solicitud.getUsuario().getCorreo();
        emailService.enviarCorreoCambioEstado(correoUsuario, solicitud.getTitulo(), nuevoEstado, mensajeAdicional);

        return solicitud;
    }

    public Solicitud asignarPromotor(Long solicitudId, Long promotorId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Usuario promotor = usuarioRepository.findById(promotorId)
                .orElseThrow(() -> new RuntimeException("Promotor no encontrado"));

        solicitud.setPromotor(promotor);
        solicitudRepository.save(solicitud);


        emailService.enviarCorreoNotificacionPromotor(promotor.getCorreo(), solicitud.getTitulo());

        return solicitud;
    }


    public List<Solicitud> obtenerTodas() {
        return solicitudRepository.findAll();
    }

    public List<Solicitud> obtenerPorEstado(String estado) {
        return solicitudRepository.findByEstado(estado);
    }

    public List<Solicitud> obtenerPorUsuario(Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId);
    }



    public Solicitud actualizarSolicitud(Long id, Solicitud solicitudActualizada) {
        Optional<Solicitud> solicitudExistente = solicitudRepository.findById(id);
        if (solicitudExistente.isPresent()) {
            Solicitud solicitud = solicitudExistente.get();

            solicitud.setTitulo(solicitudActualizada.getTitulo());
            solicitud.setNombre(solicitudActualizada.getNombre());
            solicitud.setInteresados(solicitudActualizada.getInteresados());
            solicitud.setOros(solicitudActualizada.getOros());
            solicitud.setAli1(solicitudActualizada.getAli1());
            solicitud.setAli2(solicitudActualizada.getAli2());
            solicitud.setAli3(solicitudActualizada.getAli3());
            solicitud.setAli4(solicitudActualizada.getAli4());
            solicitud.setAli5(solicitudActualizada.getAli5());
            solicitud.setAli6(solicitudActualizada.getAli6());
            solicitud.setAli7(solicitudActualizada.getAli7());
            solicitud.setAlcance(solicitudActualizada.getAlcance());
            solicitud.setEstado(solicitudActualizada.getEstado());
            solicitud.setPrioridad(solicitudActualizada.getPrioridad());

            return solicitudRepository.save(solicitud);
        } else {
            Notification.show("Solicitud no encontrada", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new RuntimeException("Solicitud no encontrada");
        }
    }


    public void eliminarSolicitud(Long id, Usuario usuario) {
        Optional<Solicitud> solicitudExistente = solicitudRepository.findById(id);
        if (solicitudExistente.isPresent()) {
            Solicitud solicitud = solicitudExistente.get();
            if (usuario.getRole().equals("PROMOTOR") || usuario.getRole().equals("CIO")) {
                solicitudRepository.delete(solicitud);
            } else {
                Notification.show("No tienes permisos para eliminar esta solicitud", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                throw new RuntimeException("No tienes permisos para eliminar esta solicitud");
            }
        } else {
            throw new RuntimeException("Solicitud no encontrada");
        }
    }

    public byte[] descargarArchivo(Long id, String tipoArchivo) {
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        switch (tipoArchivo.toLowerCase()) {
            case "memoria":
                if (solicitud.getMemoria() == null) throw new RuntimeException("El archivo de memoria no existe.");
                return solicitud.getMemoria();
            case "tecnico":
                if (solicitud.getTecnico() == null) throw new RuntimeException("El archivo técnico no existe.");
                return solicitud.getTecnico();
            case "presupuesto":
                if (solicitud.getPresupuesto() == null) throw new RuntimeException("El archivo de presupuesto no existe.");
                return solicitud.getPresupuesto();
            default:
                throw new RuntimeException("Tipo de archivo no válido. Debe ser: memoria, tecnico o presupuesto.");
        }
    }


    public List<Component> mostrarSolicitud(Solicitud solicitud){

        List<Component> lista = new ArrayList<>(List.of());

        lista.add(new H3("Título: " + solicitud.getTitulo()));

        lista.add(new Hr());

        lista.add(new Paragraph("- Importancia para el promotor: " + solicitud.getImportanciaPromotor()));

        lista.add(new Paragraph("- Interesados en el proyecto: " + solicitud.getInteresados()));
        lista.add(new Paragraph("- Financiación aportada: " + solicitud.getOros() + "€"));

        lista.add(new Hr());

        lista.add(new H3("Justificación del proyecto:"));

        List<String> alineamientos = new ArrayList<>(List.of());

        if(solicitud.getAli1() == 1) alineamientos.add("innovar, rediseñar y actualizar nuestra oferta formativa para adaptarla a las necesidades sociales y económicas de nuestro entorno");
        if(solicitud.getAli2() == 1) alineamientos.add("conseguir los niveles más altos de calidad en nuestra oferta formativa propia y reglada");
        if(solicitud.getAli3() == 1) alineamientos.add("aumentar significativamente nuestro posicionamiento en investigación y transferir de forma relevante y útil nuestra investigación a nuestro tejido social y productivo");
        if(solicitud.getAli4() == 1) alineamientos.add("consolidar un modelo de gobierno sostenible y socialmente responsable");
        if(solicitud.getAli5() == 1) alineamientos.add("conseguir que la transparencia sea un valor distintivo y relevante en la UCA");
        if(solicitud.getAli6() == 1) alineamientos.add("generar valor compartido con la Comunidad Universitaria");
        if(solicitud.getAli7() == 1) alineamientos.add("reforzar la importancia del papel de la UCA en la sociedad");

        String alisFinal = String.join(", ", alineamientos);
        alisFinal = String.join("", alisFinal, ".");
        alisFinal = alisFinal.substring(0, 1).toUpperCase() + alisFinal.substring(1);

        lista.add((new Paragraph(alisFinal)));

        lista.add(new Hr());


        lista.add(new Paragraph("Número de personas que se beneficiarían de la implantación del proyecto: " + solicitud.getAlcance()));

        lista.add(new Hr());

        lista.add(new H3("Archivos adjuntos"));

        StreamResource memoriaArchivo = new StreamResource("memoria.pdf", () -> new ByteArrayInputStream(solicitud.getMemoria()));

        Anchor enlaceMemoria = new Anchor(memoriaArchivo, "Memoria");

        StreamResource tecnicoArchivo = new StreamResource("especificaciones.pdf", () -> new ByteArrayInputStream(solicitud.getTecnico()));

        Anchor enlaceTecnico = new Anchor(tecnicoArchivo, "Especificaciones técnicas");

        StreamResource presupuestosArchivo = new StreamResource("presupuestos.pdf", () -> new ByteArrayInputStream(solicitud.getPresupuesto()));

        Anchor enlacePresupuestos = new Anchor(presupuestosArchivo, "Presupuestos");


        lista.add(enlaceMemoria);
        lista.add(enlaceTecnico);
        lista.add(enlacePresupuestos);

        lista.add(new Hr());

        return lista;
    }




    public List<Solicitud> visualizarCartera(Long usuarioId, String criterio) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean esAdministrador = usuario.getRole().equals("CIO") || usuario.getRole().equals("PROMOTOR") || usuario.getRole().equals("OTP");

        switch (criterio) {
            case "fecha":
                return esAdministrador ? solicitudRepository.findAllByOrderByCreatedAtDesc()
                        : solicitudRepository.findByUsuarioIdOrderByCreatedAtDesc(usuario.getId());
            case "importancia":
                return esAdministrador ? solicitudRepository.findAllByOrderByImportanciaPromotorDesc()
                        : solicitudRepository.findByUsuarioIdOrderByImportanciaPromotorDesc(usuario.getId());
            case "estado":
                return esAdministrador ? solicitudRepository.findAllByOrderByEstadoAsc()
                        : solicitudRepository.findByUsuarioIdOrderByEstadoAsc(usuario.getId());
            default:
                throw new RuntimeException("Criterio de ordenación no válido. Usa: fecha, importancia o estado.");
        }
    }



}
