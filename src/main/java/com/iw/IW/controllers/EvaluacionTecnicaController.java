package com.iw.IW.controllers;

import com.iw.IW.entities.EvaluacionTecnica;
import com.iw.IW.services.EvaluacionTecnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluaciones/tecnica")
public class EvaluacionTecnicaController {

    @Autowired
    private EvaluacionTecnicaService evaluacionTecnicaService;

    @PreAuthorize("hasRole('OTP')")
    @PostMapping("/{solicitudId}")
    public EvaluacionTecnica registrarEvaluacionTecnica(
            @PathVariable Long solicitudId, @RequestBody EvaluacionTecnica evaluacion) {
        return evaluacionTecnicaService.registrarEvaluacionTecnica(solicitudId, evaluacion);
    }
}
