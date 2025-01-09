package com.iw.IW.repositories;

import com.iw.IW.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByUsuarioId(Long usuarioId);

    List<Solicitud> findByEstado(String estado);
    List<Solicitud> findByUsuarioIdAndEstado(Long usuarioId, String estado);
    List<Solicitud> findByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);
    List<Solicitud> findByUsuarioIdOrderByImportanciaPromotorDesc(Long usuarioId);
    List<Solicitud> findByUsuarioIdOrderByEstadoAsc(Long usuarioId);
    List<Solicitud> findAllByOrderByCreatedAtDesc();
    List<Solicitud> findAllByOrderByImportanciaPromotorDesc();
    List<Solicitud> findAllByOrderByPrioridadDesc();
    List<Solicitud> findAllByOrderByEstadoAsc();
    List<Solicitud> findByPromotorId(Long promotorId);

}
