package com.iw.IW.views;

import com.iw.IW.entities.EvaluacionEstrategica;
import com.iw.IW.entities.EvaluacionTecnica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.EvaluacionEstrategicaRepository;
import com.iw.IW.repositories.EvaluacionTecnicaRepository;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.EvaluacionEstrategicaService;
import com.iw.IW.services.SolicitudService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.List;

@Route("ejecutar")
@PageTitle("Ejecutar proyecto")
@RolesAllowed({"CIO"})
public class EjecutarProyectoView extends VerticalLayout implements HasUrlParameter<Long> {


    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    SolicitudService solicitudService;

    @Autowired
    EvaluacionTecnicaRepository evaluacionTecnicaRepository;

    @Autowired
    EvaluacionEstrategicaService evaluacionEstrategicaService;

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        getStyle().setBackground("#d6fdff");
        setSizeUndefined();
        if (solicitudRepository.findById(parameter).isPresent()) {
            Solicitud solicitud = solicitudRepository.findById(parameter).get();

            Button volver = new Button("Volver a solicitudes");
            volver.addClickListener(e -> {
                getUI().ifPresent(ui -> ui.navigate("/ejecutar/"));
            });
            add(volver);

            add(solicitudService.mostrarSolicitud(solicitud));

            EvaluacionTecnica evTecnica = evaluacionTecnicaRepository.findBySolicitudId(solicitud.getId()).get(0);


            add(
                    new H4("Idoneidad técnica: " + evTecnica.getAlineamiento()),

                    new H4("Recursos humanos necesarios: " + evTecnica.getRecursosH()),

                    new H4("Recursos económicos necesarios: " + evTecnica.getRecursosF() + "€"));



            Button enviar = new Button("Poner en marcha proyecto", e -> {

                solicitud.setEstado("en ejecución");

                solicitudService.actualizarSolicitud(solicitud.getId(), solicitud);

                Notification.show("Proyecto puesto en marcha", 3000, Notification.Position.TOP_CENTER);
                add(new Button("Volver atrás", click -> getUI().ifPresent(ui -> ui.navigate("/ejecutar"))));
            });

            add(enviar, new HorizontalLayout(enviar));


        } else {
            throw new RuntimeException("Error: no se encontró solicitud");
        }

    }

}
