package com.iw.IW.repositories;

import com.iw.IW.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByUsuarioId(Long usuarioId);

    List<Solicitud> findByEstado(String estado);
}
