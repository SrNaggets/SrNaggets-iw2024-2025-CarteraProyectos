package com.iw.IW.repositories;

import com.iw.IW.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByEstado(String estado);

    List<Proyecto> findBySolicitudId(Long solicitudId);
}
