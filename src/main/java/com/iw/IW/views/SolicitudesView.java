package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
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

        Button principal = new Button("Volver a menú principal", click -> getUI().ifPresent(ui -> ui.navigate("")));

        add(new HorizontalLayout(logout, principal));

        List<Solicitud> aux = solicitudRepository.findByPromotorId(usuarioRepository.findByCorreo(securityService.getAuthenticatedUser().getUsername()).get().getId());

        add(new H2("Solicitudes pendientes de evaluar:"));

        if(aux.isEmpty()){
            add(new H4("No quedan solicitudes pendientes de avalación."));
        }
        else{
            HorizontalLayout solicitudes = new HorizontalLayout();

            Grid<Solicitud> gridSolicitudes = new Grid<>(Solicitud.class, false);
            gridSolicitudes.addColumn(new ComponentRenderer<>(p -> new Anchor("/avalar/" + p.getId(), p.getTitulo()))).setHeader("Título");

            gridSolicitudes.addColumn(Solicitud::getInteresados).setHeader("Interesados");
            gridSolicitudes.addColumn(Solicitud::getImportanciaPromotor).setHeader("Importancia para el promotor");

            gridSolicitudes.setItems(aux);

        /*
        for(Solicitud solicitud : aux){
            if(solicitud.getEstado().equals("solicitado")){
                Button boton = new Button(solicitud.getNombre());
                boton.addClickListener(e -> {
                    getUI().ifPresent(ui -> ui.navigate("/avalar/" + solicitud.getId()));
                });
                solicitudes.add(boton);
            }

        }*/

            add(gridSolicitudes);
        }


    }

}
