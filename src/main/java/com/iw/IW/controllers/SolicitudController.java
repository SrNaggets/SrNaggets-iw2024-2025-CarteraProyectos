package com.iw.IW.controllers;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.services.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @PostMapping("/crear")
    public Solicitud crearSolicitud(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("titulo") String titulo,
            @RequestParam("nombre") String nombre,
            @RequestParam("interesados") String interesados,
            @RequestParam("oros") Long oros,
            @RequestParam("ali1") Integer ali1,
            @RequestParam("ali2") Integer ali2,
            @RequestParam("ali3") Integer ali3,
            @RequestParam("ali4") Integer ali4,
            @RequestParam("ali5") Integer ali5,
            @RequestParam("ali6") Integer ali6,
            @RequestParam("ali7") Integer ali7,
            @RequestParam("alcance") String alcance,
            @RequestParam(value = "importanciaPromotor", required = false) Integer importanciaPromotor,
            @RequestParam("memoria") MultipartFile memoria,
            @RequestParam("tecnico") MultipartFile tecnico,
            @RequestParam("presupuesto") MultipartFile presupuesto) throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        return solicitudService.crearSolicitud(
                titulo, nombre, interesados, oros, ali1, ali2, ali3, ali4, ali5, ali6, ali7,
                alcance, importanciaPromotor, usuario,
                memoria.getBytes(), tecnico.getBytes(), presupuesto.getBytes()
        );
    }

    @GetMapping("/todas")
    public List<Solicitud> obtenerTodas() {
        return solicitudService.obtenerTodas();
    }

    @GetMapping("/estado/{estado}")
    public List<Solicitud> obtenerPorEstado(@PathVariable String estado) {
        return solicitudService.obtenerPorEstado(estado);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Solicitud> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return solicitudService.obtenerPorUsuario(usuarioId);
    }

    @PutMapping("/actualizar/{id}")
    public Solicitud actualizarSolicitud(@PathVariable Long id, @RequestBody Solicitud solicitudActualizada) {
        return solicitudService.actualizarSolicitud(id, solicitudActualizada);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarSolicitud(@PathVariable Long id, @RequestParam Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        solicitudService.eliminarSolicitud(id, usuario);
    }


    @GetMapping("/{id}/archivo/{tipoArchivo}")
    public ResponseEntity<byte[]> descargarArchivo(@PathVariable Long id, @PathVariable String tipoArchivo) {
        byte[] archivo = solicitudService.descargarArchivo(id, tipoArchivo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + tipoArchivo + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(archivo);
    }
}
