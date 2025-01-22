package com.iw.IW.controllers;

import com.iw.IW.entities.Usuario;
import com.iw.IW.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/promotores/nombre/{nombre}")
    public List<Usuario> buscarPromotoresPorNombre(@PathVariable String nombre) {
        return usuarioService.buscarPromotoresPorNombre(nombre);
    }


    @GetMapping("/promotores/correo/{correo}")
    public List<Usuario> buscarPromotoresPorCorreo(@PathVariable String correo) {
        return usuarioService.buscarPromotoresPorCorreo(correo);
    }
    
    @PostMapping("/recuperar-contraseña")
    public void reiniciarContraseña(@RequestParam String correo) {
        usuarioService.reiniciarContraseña(correo);
    }

    @PutMapping("/{id}/modificar")
    public Usuario modificarUsuario(
            @PathVariable Long id,
            @RequestParam(required = false) String nuevoNombre,
            @RequestParam(required = false) String nuevaContraseña) {
        return usuarioService.modificarUsuario(id, nuevoNombre, nuevaContraseña);
    }


    @GetMapping("/promotores/{id}")
    public Usuario buscarPromotorPorId(@PathVariable Long id) {
        return usuarioService.buscarPromotorPorId(id);
    }

    @PutMapping("/cambiar-rol")
    public Usuario cambiarRolUsuario(
            @RequestParam String correoUsuario,
            @RequestParam String nuevoRol,
            @RequestParam Long idSolicitante) {
        return usuarioService.cambiarRolUsuario(correoUsuario, nuevoRol, idSolicitante);
    }
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('CIO')")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
