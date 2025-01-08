package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.List;

@Route("avalar")
@PageTitle("Avalar solicitudes")
@RolesAllowed({"PROMOTOR"})
public class SolicitudesView extends VerticalLayout {


    public SolicitudesView(@Autowired SecurityService securityService, @Autowired SolicitudRepository solicitudRepository,
                           @Autowired UsuarioRepository usuarioRepository){

        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.AUTO);

        Button logout = new Button("Logout", click -> securityService.logout());
        add(logout);

        List<Solicitud> aux = solicitudRepository.findByPromotorId(usuarioRepository.findByNombre(securityService.getAuthenticatedUser().getUsername()).getId());

        add(new H2("Solicitudes pendientes de evaluar:"));

        HorizontalLayout solicitudes = new HorizontalLayout();

        for(Solicitud solicitud : aux){
            if(solicitud.getEstado().equals("solicitado")){
                Button boton = new Button(solicitud.getNombre());
                boton.addClickListener(e -> {
                    getUI().ifPresent(ui -> ui.navigate("/avalar/" + solicitud.getId()));
                });
                solicitudes.add(boton);
            }

        }

        add(solicitudes);
    }

}
