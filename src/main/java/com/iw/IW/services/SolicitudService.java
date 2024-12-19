package com.iw.IW.services;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    public Solicitud crearSolicitud(
            String titulo, String nombre, String interesados, Long oros, Integer ali1, Integer ali2, Integer ali3,
            Integer ali4, Integer ali5, Integer ali6, Integer ali7, String alcance, Integer importanciaPromotor,
            Usuario usuario, byte[] memoria, byte[] tecnico, byte[] presupuesto) {

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

        return solicitudRepository.save(solicitud);
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

            return solicitudRepository.save(solicitud);
        } else {
            throw new RuntimeException("Solicitud no encontrada");
        }
    }

    public void eliminarSolicitud(Long id, Usuario usuario) {
        Optional<Solicitud> solicitudExistente = solicitudRepository.findById(id);
        if (solicitudExistente.isPresent()) {
            Solicitud solicitud = solicitudExistente.get();
            if (usuario.getRole().equals("promotor") || usuario.getRole().equals("CIO")) {
                solicitudRepository.delete(solicitud);
            } else {
                throw new RuntimeException("No tienes permisos para eliminar esta solicitud");
            }
        } else {
            throw new RuntimeException("Solicitud no encontrada");
        }
    }

    public byte[] descargarArchivo(Long id, String tipoArchivo) {
        Optional<Solicitud> solicitudOpt = solicitudRepository.findById(id);
        if (solicitudOpt.isEmpty()) {
            throw new RuntimeException("Solicitud no encontrada");
        }

        Solicitud solicitud = solicitudOpt.get();

        switch (tipoArchivo.toLowerCase()) {
            case "memoria":
                return solicitud.getMemoria();
            case "tecnico":
                return solicitud.getTecnico();
            case "presupuesto":
                return solicitud.getPresupuesto();
            default:
                throw new RuntimeException("Tipo de archivo no válido");
        }
    }
}
