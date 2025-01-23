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

@Route("evaluacionestrategica")
@PageTitle("Evaluación estratégica de solicitud")
@RolesAllowed({"CIO"})
public class EstrategicaView extends VerticalLayout implements HasUrlParameter<Long> {


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
                getUI().ifPresent(ui -> ui.navigate("/evaluacionestrategica/"));
            });
            add(volver);

            add(solicitudService.mostrarSolicitud(solicitud));

            EvaluacionTecnica evTecnica = evaluacionTecnicaRepository.findBySolicitudId(solicitud.getId()).get(0);


            add(
            new H4("Idoneidad técnica: " + evTecnica.getAlineamiento()),

            new H4("Recursos humanos necesarios: " + evTecnica.getRecursosH()),

            new H4("Recursos económicos necesarios: " + evTecnica.getRecursosF() + "€"));

            TextField descripcion = new TextField("Evaluación estratégica: ");
            descripcion.addClassName("bordered");
            descripcion.setPlaceholder("");
            descripcion.setWidth("70%");

            Button enviar = new Button("Enviar evaluación estratégica", e -> {

                EvaluacionEstrategica evaluacionEstrategica = new EvaluacionEstrategica();
                evaluacionEstrategica.setAlineamiento(descripcion.getValue());
                evaluacionEstrategica.setId(solicitud.getId());

                evaluacionEstrategicaService.registrarEvaluacionEstrategica(solicitud.getId(), evaluacionEstrategica);

                Notification.show("Evaluación estratégica creada con éxito", 3000, Notification.Position.TOP_CENTER);
                add(new Button("Volver atrás", click -> getUI().ifPresent(ui -> ui.navigate("/evaluacionestrategica"))));
            });

            Button cancelar = new Button("Cancelar solicitud", e -> {

                solicitudService.cambiarEstado(solicitud.getId(), "cancelado", null);

                getUI().ifPresent(ui -> ui.navigate("/evaluacionestrategica/"));
            });

            cancelar.addThemeVariants(ButtonVariant.LUMO_ERROR);

            add(descripcion, new HorizontalLayout(enviar, cancelar));


        } else {
            throw new RuntimeException("Error: no se encontró solicitud");
        }

    }

}
