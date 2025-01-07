package com.iw.IW.controllers;

import com.iw.IW.entities.Usuario;
import com.iw.IW.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public Usuario registrarUsuario(@RequestParam String correo, @RequestParam String nombre, @RequestParam String contraseña) {
        return usuarioService.registrarUsuario(correo, nombre, contraseña);
    }

    @PostMapping("/verify")
    public String verificarUsuario(@RequestParam String correo, @RequestParam String codigo) {
        usuarioService.verificarUsuario(correo, codigo);
        return "Usuario verificado correctamente";
    }

    @PostMapping("/login")
    public Usuario autenticarUsuario(@RequestParam String correo, @RequestParam String contraseña) {
        return usuarioService.autenticarUsuario(correo, contraseña);
    }
}
