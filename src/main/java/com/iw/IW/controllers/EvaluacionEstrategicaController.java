package com.iw.IW.controllers;

import com.iw.IW.entities.EvaluacionEstrategica;
import com.iw.IW.services.EvaluacionEstrategicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
@RequestMapping("/evaluaciones/estrategica")
public class EvaluacionEstrategicaController {

    @Autowired
    private EvaluacionEstrategicaService evaluacionEstrategicaService;

    @PreAuthorize("hasRole('CIO')")
    @PostMapping("/{solicitudId}")
    public EvaluacionEstrategica registrarEvaluacionEstrategica(
            @PathVariable Long solicitudId, @RequestBody EvaluacionEstrategica evaluacion) {
        return evaluacionEstrategicaService.registrarEvaluacionEstrategica(solicitudId, evaluacion);
    }
}
