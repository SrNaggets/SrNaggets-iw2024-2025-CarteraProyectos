package com.iw.IW.views;

import com.iw.IW.entities.EvaluacionTecnica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.services.EvaluacionTecnicaService;
import com.iw.IW.services.SolicitudService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
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

@Route("evaluaciontecnica")
@PageTitle("Evaluación técnica de solicitud")
@RolesAllowed({"OTP"})
public class TecnicaView extends VerticalLayout implements HasUrlParameter<Long> {

    @Autowired
    SolicitudService solicitudService;

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    EvaluacionTecnicaService evaluacionTecnicaService;

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        getStyle().setBackground("#d6fdff");
        setSizeUndefined();
        if (solicitudRepository.findById(parameter).isPresent()) {
            Solicitud solicitud = solicitudRepository.findById(parameter).get();


            Button volver = new Button("Volver a solicitudes");
            volver.addClickListener(e -> {
                getUI().ifPresent(ui -> ui.navigate("/evaluaciontecnica/"));
            });
            add(volver);


            add(solicitudService.mostrarSolicitud(solicitud));


            TextField Alineamiento = new TextField("Idoneidad técnica:");
            Alineamiento.addClassName("bordered");
            Alineamiento.setPlaceholder("");
            Alineamiento.setWidth("70%");

            TextField RecursosH = new TextField("Recursos humanos necesarios:");
            RecursosH.addClassName("bordered");
            RecursosH.setPlaceholder("");
            RecursosH.setWidth("70%");

            NumberField RecursosF = new NumberField("Recursos económicos necesarios:");
            Div sufijoEuro = new Div();
            sufijoEuro.setText("€");
            RecursosF.setSuffixComponent(sufijoEuro);
            RecursosF.addClassName("bordered");
            RecursosF.setPlaceholder("");
            RecursosF.setWidth("30%");


            Button enviar = new Button("Enviar evaluación técnica", e -> {

                EvaluacionTecnica evaluacionTecnica = new EvaluacionTecnica();
                evaluacionTecnica.setId(solicitud.getId());
                evaluacionTecnica.setAlineamiento(Alineamiento.getValue());
                evaluacionTecnica.setRecursosF(RecursosF.getValue().longValue());
                evaluacionTecnica.setRecursosH(RecursosH.getValue());

                evaluacionTecnicaService.registrarEvaluacionTecnica(solicitud.getId(), evaluacionTecnica);


                Notification.show("Evaluación técnica creada con éxito", 3000, Notification.Position.TOP_CENTER);
                add(new Button("Volver atrás", click -> getUI().ifPresent(ui -> ui.navigate("/evaluaciontecnica"))));
            });

            add(Alineamiento, RecursosH, RecursosF, enviar);


        } else {
            throw new RuntimeException("Error: no se encontró solicitud");
        }

    }
}
