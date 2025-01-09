package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
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

@Route("priorizar")
@PageTitle("Priorizar proyectos")
@RolesAllowed({"CIO"})
public class CIOPriorizarView extends VerticalLayout {

    public CIOPriorizarView(@Autowired SecurityService securityService, @Autowired SolicitudService solicitudService){
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.AUTO);

        Button logout = new Button("Logout", click -> securityService.logout());
        Button principal = new Button("Volver a menú principal", click -> getUI().ifPresent(ui -> ui.navigate("")));

        add(new HorizontalLayout(logout, principal));

        List<Solicitud> aux = solicitudService.obtenerPorEstado("evaluado");

        add(new H2("Proyectos a priorizar:"));

        if(aux.isEmpty()){
            add(new H4("No hay proyectos."));
        }
        else{


            Grid<Solicitud> gridSolicitudes = new Grid<>(Solicitud.class, false);
            gridSolicitudes.addColumn(new ComponentRenderer<>(p -> new Anchor("/priorizar/" + p.getId(), p.getTitulo()))).setHeader("Título");

            gridSolicitudes.addColumn(Solicitud::getInteresados).setHeader("Interesados");
            gridSolicitudes.addColumn(Solicitud::getPrioridad).setHeader("Prioridad");

            gridSolicitudes.setItems(aux);

            add(gridSolicitudes);
        }
    }
}
