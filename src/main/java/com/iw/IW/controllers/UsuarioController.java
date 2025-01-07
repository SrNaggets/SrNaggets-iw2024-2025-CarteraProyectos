package com.iw.IW.controllers;

import com.iw.IW.entities.Usuario;
import com.iw.IW.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/promotores/{id}")
    public Usuario buscarPromotorPorId(@PathVariable Long id) {
        return usuarioService.buscarPromotorPorId(id);
    }
}
