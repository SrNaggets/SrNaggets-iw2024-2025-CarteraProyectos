package com.iw.IW.services;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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




    public List<Solicitud> visualizarCartera(Authentication authentication, String criterio) {
            String correoUsuario = authentication.getName();
            Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
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
