package com.iw.IW.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarCorreoVerificacion(String destinatario, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("carteradeproyectosiw@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de Verificación");
        mensaje.setText("Tu código de verificación es: " + codigo);
        mailSender.send(mensaje);
    }

    @Async
    public void enviarCorreoCambioEstado(String destinatario, String tituloSolicitud, String nuevoEstado, String mensajeAdicional) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("carteradeproyectosiw@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Actualización del Estado de tu Solicitud");
        mensaje.setText("Hola,\n\nEl estado de tu solicitud '" + tituloSolicitud + "' ha cambiado a: " + nuevoEstado +
                (mensajeAdicional != null ? "\n\nMensaje adicional: " + mensajeAdicional : "") +
                "\n\nGracias.");
        mailSender.send(mensaje);
    }
    @Async
    public void enviarCorreoEvaluacionTecnica(String destinatario, String tituloSolicitud, String descripcion, String recursosH, Long recursosF, String alineamiento) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("carteradeproyectosiw@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Evaluación Técnica Registrada");
        mensaje.setText("Hola,\n\nSe ha registrado una nueva evaluación técnica para tu solicitud: '" + tituloSolicitud + "'." +
                "\n\nDescripción: " + descripcion +
                "\nRecursos Humanos: " + recursosH +
                "\nRecursos Financieros: " + (recursosF != null ? recursosF : "No especificado") +
                "\nAlineamiento: " + (alineamiento != null ? alineamiento : "No especificado") +
                "\n\nGracias.");
        mailSender.send(mensaje);
    }
    @Async
    public void enviarCorreoEliminacionUsuario(String destinatario, String nombre) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Notificación de Eliminación de Cuenta");
        mensaje.setText("Hola " + nombre + ",\n\nTu cuenta ha sido eliminada por el administrador (CIO).\nSi crees que esto es un error, contacta con soporte.\n\nGracias.");
        mailSender.send(mensaje);
    }

    @Async
    public void enviarCorreoEvaluacionEstrategica(String destinatario, String tituloSolicitud, String descripcion, String alineamiento) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("carteradeproyectosiw@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Evaluación Estratégica Registrada");
        mensaje.setText("Hola,\n\nSe ha registrado una nueva evaluación estratégica para tu solicitud: '" + tituloSolicitud + "'." +
                "\n\nDescripción: " + descripcion +
                "\nAlineamiento Estratégico: " + alineamiento +
                "\n\nGracias.");
        mailSender.send(mensaje);
    }
    @Async
    public void enviarCorreoNotificacionPromotor(String destinatario, String tituloSolicitud) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("carteradeproyectosiw@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Nueva Solicitud Asignada para Evaluación");
        mensaje.setText("Hola,\n\nHas sido asignado como promotor para la solicitud: '" + tituloSolicitud + "'." +
                "\nPor favor, accede al sistema para realizar la evaluación correspondiente." +
                "\n\nGracias.");
        mailSender.send(mensaje);
    }
    @Async
    public void enviarCorreoRecuperacionContraseña(String destinatario, String nuevaContraseña) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("carteradeproyectosiw@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Recuperación de Contraseña");
        mensaje.setText("Hola,\n\nTu contraseña ha sido reiniciada. Tu nueva contraseña es: " + nuevaContraseña +
                "\n\nPor favor, cámbiala después de iniciar sesión.\n\nGracias.");
        mailSender.send(mensaje);
    }
    @Async
    public void enviarCorreoCambioRol(String destinatario, String nuevoRol) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("carteradeproyectosiw@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Cambio de Rol en el Sistema");
        mensaje.setText("Hola,\n\nTu rol en el sistema ha sido cambiado a: " + nuevoRol + ".\n\nGracias.");
        mailSender.send(mensaje);
    }


}
