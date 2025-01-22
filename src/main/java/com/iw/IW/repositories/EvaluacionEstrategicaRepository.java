package com.iw.IW.repositories;

import com.iw.IW.entities.EvaluacionEstrategica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluacionEstrategicaRepository extends JpaRepository<EvaluacionEstrategica, Long> {
    List<EvaluacionEstrategica> findByIdS(Long idS);
}
