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
@RolesAllowed({"promotor"})
public class AvalarView extends VerticalLayout implements HasUrlParameter<Long> {

    @Autowired
    SolicitudRepository solicitudRepository;

    @Autowired
    SolicitudService solicitudService;


    @Override
    public void setParameter(BeforeEvent event, Long parameter){

        if(solicitudRepository.findById(parameter).isPresent()){
            Solicitud solicitud = solicitudRepository.findById(parameter).get();

            Button volver = new Button("Volver a solicitudes");
            volver.addClickListener(e -> {
                getUI().ifPresent(ui -> ui.navigate("/avalar/"));
            });
            add(volver);


            add(    new H3("Título: " + solicitud.getTitulo()),

                    new H4("Importancia para el promotor: " + solicitud.getImportanciaPromotor()),

                    new H4("Interesados en el proyecto: " + solicitud.getInteresados()),
                    new H4("Financiación aportada: " + solicitud.getOros() + "€"),

                    new H3("Justificación del proyecto:"));

            List<String> alineamientos = new java.util.ArrayList<>(List.of());

            if(solicitud.getAli1() == 1) alineamientos.add("innovar, rediseñar y actualizar nuestra oferta formativa para adaptarla a las necesidades sociales y económicas de nuestro entorno");
            if(solicitud.getAli2() == 1) alineamientos.add("conseguir los niveles más altos de calidad en nuestra oferta formativa propia y reglada");
            if(solicitud.getAli3() == 1) alineamientos.add("aumentar significativamente nuestro posicionamiento en investigación y transferir de forma relevante y útil nuestra investigación a nuestro tejido social y productivo");
            if(solicitud.getAli4() == 1) alineamientos.add("consolidar un modelo de gobierno sostenible y socialmente responsable");
            if(solicitud.getAli5() == 1) alineamientos.add("conseguir que la transparencia sea un valor distintivo y relevante en la UCA");
            if(solicitud.getAli6() == 1) alineamientos.add("generar valor compartido con la Comunidad Universitaria");
            if(solicitud.getAli7() == 1) alineamientos.add("reforzar la importancia del papel de la UCA en la sociedad");

            String alisFinal = String.join(", ", alineamientos);
            alisFinal = String.join("", alisFinal, ".");
            alisFinal = alisFinal.substring(0, 1).toUpperCase() + alisFinal.substring(1);

            add(new Paragraph(alisFinal));

            add(
                    new H4("Número de personas que se beneficiarían de la implantación del proyecto: " + solicitud.getAlcance()),


                    new H3("Archivos adjuntos"));

            StreamResource memoriaArchivo = new StreamResource("memoria.pdf", () -> new ByteArrayInputStream(solicitud.getMemoria()));

            Anchor enlaceMemoria = new Anchor(memoriaArchivo, "Memoria");

            StreamResource tecnicoArchivo = new StreamResource("especificaciones.pdf", () -> new ByteArrayInputStream(solicitud.getTecnico()));

            Anchor enlaceTecnico = new Anchor(tecnicoArchivo, "Especificaciones técnicas");

            StreamResource presupuestosArchivo = new StreamResource("presupuestos.pdf", () -> new ByteArrayInputStream(solicitud.getPresupuesto()));

            Anchor enlacePresupuestos = new Anchor(presupuestosArchivo, "Presupuestos");

            add(
                    enlaceMemoria,
                    enlaceTecnico,
                    enlacePresupuestos);

            Button aprobar = new Button("Aprobar");
            aprobar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

            aprobar.addClickListener(aprobado -> {
                solicitudService.cambiarEstado(solicitud.getId(), "pendiente de evaluación técnica", null);
                getUI().ifPresent(ui -> ui.navigate("/avalar/"));
            });

            Button rechazar = new Button("Rechazar");
            rechazar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

            rechazar.addClickListener(rechazado -> {
                solicitudService.eliminarSolicitud(solicitud.getId(), solicitud.getUsuario());
            });

            add(new HorizontalLayout(aprobar, rechazar));

        }
        else{
            throw new RuntimeException("Error: no se encontró solicitud");
        }

    }


}
