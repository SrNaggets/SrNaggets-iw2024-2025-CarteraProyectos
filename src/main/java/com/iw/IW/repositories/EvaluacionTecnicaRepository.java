package com.iw.IW.repositories;

import com.iw.IW.entities.EvaluacionTecnica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluacionTecnicaRepository extends JpaRepository<EvaluacionTecnica, Long> {
    List<EvaluacionTecnica> findBySolicitudId(Long solicitudId);
}
