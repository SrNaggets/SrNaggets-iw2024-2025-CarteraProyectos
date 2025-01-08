package com.iw.IW.views;

import com.iw.IW.entities.Proyecto;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.ProyectoRepository;
import com.iw.IW.services.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("proyectos")
@PageTitle("Proyectos en marcha")
@RolesAllowed({"CIO"})
public class ProyectosCIOView extends VerticalLayout {

    public ProyectosCIOView(@Autowired SecurityService securityService, @Autowired ProyectoRepository proyectoRepository){
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.AUTO);

        Button logout = new Button("Logout", click -> securityService.logout());
        add(logout);

        List<Proyecto> aux = proyectoRepository.findByEstado("en marcha");

        add(new H2("Solicitudes pendientes de evaluación estratégica:"));

        HorizontalLayout solicitudes = new HorizontalLayout();

        for(Proyecto solicitud : aux){
            Button boton = new Button(solicitud.getSolicitud().getNombre());
            boton.addClickListener(e -> {
                getUI().ifPresent(ui -> ui.navigate("/proyectos/" + solicitud.getId()));
            });
            solicitudes.add(boton);
        }

        add(solicitudes);
    }

}
