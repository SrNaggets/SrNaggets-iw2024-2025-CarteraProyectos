package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.SolicitudService;
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

@Route("evaluacionestrategica")
@PageTitle("Evaluación estratégica de solicitudes")
@RolesAllowed({"CIO"})
public class EstrategicasView extends VerticalLayout {

    public EstrategicasView(@Autowired SecurityService securityService, @Autowired SolicitudService solicitudService){
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.AUTO);

        Button logout = new Button("Logout", click -> securityService.logout());
        Button principal = new Button("Volver a menú principal", click -> getUI().ifPresent(ui -> ui.navigate("")));

        add(new HorizontalLayout(logout, principal));

        List<Solicitud> aux = solicitudService.obtenerPorEstado("pendiente de evaluación estratégica");

        add(new H2("Solicitudes pendientes de evaluación estratégica:"));

        HorizontalLayout solicitudes = new HorizontalLayout();

        for(Solicitud solicitud : aux){
            Button boton = new Button(solicitud.getNombre());
            boton.addClickListener(e -> {
                getUI().ifPresent(ui -> ui.navigate("/evaluacionestrategica/" + solicitud.getId()));
            });
            solicitudes.add(boton);
        }

        add(solicitudes);
    }
}
