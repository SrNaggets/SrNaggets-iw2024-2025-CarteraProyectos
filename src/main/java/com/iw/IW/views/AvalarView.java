package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.EmailService;
import com.iw.IW.services.SolicitudService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.List;

@Route("avalar")
@PageTitle("Avalar solicitud")
@RolesAllowed({"PROMOTOR"})
public class AvalarView extends VerticalLayout implements HasUrlParameter<Long> {

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    SolicitudService solicitudService;


    @Override
    public void setParameter(BeforeEvent event, Long parameter){
        getStyle().setBackground("#d6fdff");
        setSizeUndefined();

        if(solicitudRepository.findById(parameter).isPresent()){
            Solicitud solicitud = solicitudRepository.findById(parameter).get();

            Button volver = new Button("Volver a solicitudes");
            volver.addClickListener(e -> {
                getUI().ifPresent(ui -> ui.navigate("/avalar/"));
            });
            add(volver);


            add(solicitudService.mostrarSolicitud(solicitud));

            Button aprobar = new Button("Aprobar");
            aprobar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

            aprobar.addClickListener(aprobado -> {
                solicitudService.cambiarEstado(solicitud.getId(), "pendiente de evaluación técnica", null);
                getUI().ifPresent(ui -> ui.navigate("/avalar/"));
            });

            Button rechazar = new Button("Rechazar");
            rechazar.addThemeVariants(ButtonVariant.LUMO_ERROR);

            rechazar.addClickListener(rechazado -> {
                solicitudService.cambiarEstado(solicitud.getId(), "rechazado", null);
                getUI().ifPresent(ui -> ui.navigate("/avalar/"));
            });

            add(new HorizontalLayout(aprobar, rechazar));

        }
        else{
            throw new RuntimeException("Error: no se encontró solicitud");
        }

    }


}
