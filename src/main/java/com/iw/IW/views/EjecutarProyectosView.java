package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.SolicitudService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("ejecutar")
@PageTitle("Ejecutar proyectos")
@RolesAllowed({"CIO"})
public class EjecutarProyectosView extends VerticalLayout {

    public EjecutarProyectosView(@Autowired SecurityService securityService, @Autowired SolicitudService solicitudService, @Autowired SolicitudRepository solicitudRepository){
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.AUTO);
        getStyle().setBackground("#d6fdff");
        setSizeUndefined();

        Button logout = new Button("Logout", click -> securityService.logout());
        Button principal = new Button("Volver a menú principal", click -> getUI().ifPresent(ui -> ui.navigate("")));

        add(new HorizontalLayout(logout, principal));

        List<Solicitud> aux = solicitudRepository.findByEstado("priorizado");



        add(new H2("Proyectos a ejecutar:"));

        if(aux.isEmpty()){
            add(new H4("No hay proyectos."));
        }
        else{


            Grid<Solicitud> gridSolicitudes = new Grid<>(Solicitud.class, false);
            gridSolicitudes.addColumn(new ComponentRenderer<>(p -> new Anchor("/ejecutar/" + p.getId(), p.getTitulo()))).setHeader("Título");

            gridSolicitudes.addColumn(Solicitud::getEstado).setHeader("Estado");
            gridSolicitudes.addColumn(Solicitud::getPrioridad).setHeader("Prioridad");

            gridSolicitudes.setItems(aux);

            add(gridSolicitudes);
        }
    }
}
