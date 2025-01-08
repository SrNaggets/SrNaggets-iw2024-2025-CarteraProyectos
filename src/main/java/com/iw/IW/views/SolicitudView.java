package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.SolicitudService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Route("solicitud")
@PageTitle("Formulario de solicitud")
@RolesAllowed({"USER", "ADMIN", "normal", "PROMOTOR"})
public class SolicitudView extends VerticalLayout {

    @Autowired SecurityService securityService;

    @Autowired UsuarioRepository usuarioRepository;

    private byte[] memoriaBytes;
    private byte[] tecnicoBytes;
    private byte[] presupuestoBytes;

    public SolicitudView(@Autowired SecurityService securityService, @Autowired UsuarioRepository usuarioRepository, @Autowired SolicitudService solicitudService) {


        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;

        setSizeUndefined();
        setAlignItems(Alignment.AUTO);
        setJustifyContentMode(JustifyContentMode.CENTER);

        UserDetails user = securityService.getAuthenticatedUser();

        if (user != null) {

            Button logout = new Button("Logout", click ->
                    securityService.logout());

            add(new HorizontalLayout(logout, new Paragraph(user.getUsername()), new Paragraph(user.getAuthorities().toString())));

        }

        TextField titulo = new TextField("Título del proyecto:");
        titulo.addClassName("bordered");
        titulo.setPlaceholder("Título descriptivo");
        titulo.setWidth("30%");

        TextField nombreCorto = new TextField("Nombre corto:");
        nombreCorto.addClassName("bordered");
        nombreCorto.setPlaceholder("Nombre corto o acrónimo que identifique el proyecto");
        nombreCorto.setWidth("30%");


        TextField nombreSol = new TextField("Nombre del solicitante:");
        nombreSol.addClassName("bordered");
        nombreSol.setPlaceholder("Introduzca el nombre y apellidos");
        nombreSol.setWidth("50%");

        TextField correoSol = new TextField("Correo del solicitante:");
        correoSol.addClassName("bordered");
        correoSol.setPlaceholder("solicitante@uca.es");
        correoSol.setWidth("50%");

        TextField unidadSol = new TextField("Unidad del solicitante:");
        unidadSol.addClassName("bordered");
        unidadSol.setPlaceholder("Unidad/centro del solicitante");
        unidadSol.setWidth("50%");


        ComboBox<String> promotor = new ComboBox<>("Promotor:");

        List<String> auxLista = new ArrayList<>();
        for(Usuario usuario : usuarioRepository.findByRole("promotor")){
            auxLista.add(usuario.getNombre());
        }

        promotor.setItems(auxLista);
        promotor.setWidth("50%");

        NumberField importanciaProm = new NumberField("Importancia para el promotor:");
        importanciaProm.setPlaceholder("Indique un valor entre 0 y 5");
        importanciaProm.setMin(0);
        importanciaProm.setMax(5);


        TextField interesados = new TextField("Interesados:");
        interesados.addClassName("bordered");
        interesados.setPlaceholder("Nombre de Unidades, Centros o Áreas que participan en el proyecto");
        interesados.setWidth("50%");

        NumberField financiacion = new NumberField("Financiación aportada:");
        Div sufijoEuro = new Div();
        sufijoEuro.setText("€");
        financiacion.setSuffixComponent(sufijoEuro);
        financiacion.setWidth("30%");

        CheckboxGroup<String> alineamiento = new CheckboxGroup<>("Alineamiento con los objetivos estratégicos:");

        alineamiento.setItems("Innovar, rediseñar y actualizar nuestra oferta formativa para adaptarla a las necesidades sociales y económicas de nuestro entorno.",
                "Conseguir los niveles más altos de calidad en nuestra oferta formativa propia y reglada.",
                "Aumentar significativamente nuestro posicionamiento en investigación y transferir de forma relevante y útil nuestra investigación a nuestro tejido social y productivo.",
                "Consolidar un modelo de gobierno sostenible y socialmente responsable.",
                "Conseguir que la transparencia sea un valor distintivo y relevante en la UCA.",
                "Generar valor compartido con la Comunidad Universitaria.",
                "Reforzar la importancia del papel de la UCA en la sociedad.");
        alineamiento.setLabel("Su solicitud debe estar alineada con, al menos, uno de los anteriores objetivos estratégicos");

        alineamiento.addClassName("checkbox-group");


        TextField alcance = new TextField("Alcance:");
        alcance.addClassName("bordered");
        alcance.setPlaceholder("");
        alcance.setLabel("Número de personas que se beneficiarían de la implantación del proyecto");
        alcance.setWidth("50%");

        DatePicker fechaPuesta = new DatePicker("Fecha requerida de puesta en marcha:");
        fechaPuesta.setHelperText("Solo rellenar en caso de que la motivación sea por obligado cumplimiento de normativa");

        TextField normativa = new TextField("Normativa de aplicación:");
        normativa.setPlaceholder("Código de referencia y/o descriptor de la normativa de aplicación obligatoria");
        normativa.setWidth("50%");


        FileBuffer buffer1 = new FileBuffer();
        Upload memoria = new Upload(buffer1);
        memoria.setDropAllowed(true);
        memoria.setAcceptedFileTypes("application/pdf", ".pdf");


        FileBuffer buffer2 = new FileBuffer();
        Upload tecnico = new Upload(buffer2);
        tecnico.setDropAllowed(true);
        tecnico.setAcceptedFileTypes("application/pdf", ".pdf");


        FileBuffer buffer3 = new FileBuffer();
        Upload presupuestos = new Upload(buffer3);
        presupuestos.setDropAllowed(true);
        presupuestos.setAcceptedFileTypes("application/pdf", ".pdf");


        memoria.addSucceededListener(memoriaExito -> {
            InputStream inputStream = buffer1.getInputStream();
            try {
                this.memoriaBytes = inputStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        memoria.addFileRejectedListener(memoriaFallo -> {
            String errorMessage = memoriaFallo.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        tecnico.addSucceededListener(tecnicoExito -> {
            InputStream inputStream = buffer2.getInputStream();
            try {
                this.tecnicoBytes = inputStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        tecnico.addFileRejectedListener(tecnicoFallo -> {
            String errorMessage = tecnicoFallo.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });


        presupuestos.addSucceededListener(presupExito -> {
            InputStream inputStream = buffer3.getInputStream();
            try {
                this.presupuestoBytes = inputStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        presupuestos.addFileRejectedListener(presupFallo -> {
            String errorMessage = presupFallo.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });


        // Button click listeners can be defined as lambda expressions
        Button enviar = new Button("Enviar formulario", e -> {

            Set<String> alineamientos = alineamiento.getValue();
            Integer[] alis = {0, 0, 0, 0, 0, 0, 0};

            String[] alineamientosTodos = {
            "Innovar, rediseñar y actualizar nuestra oferta formativa para adaptarla a las necesidades sociales y económicas de nuestro entorno.",
            "Conseguir los niveles más altos de calidad en nuestra oferta formativa propia y reglada.",
            "Aumentar significativamente nuestro posicionamiento en investigación y transferir de forma relevante y útil nuestra investigación a nuestro tejido social y productivo.",
            "Consolidar un modelo de gobierno sostenible y socialmente responsable.",
            "Conseguir que la transparencia sea un valor distintivo y relevante en la UCA.",
            "Generar valor compartido con la Comunidad Universitaria.",
            "Reforzar la importancia del papel de la UCA en la sociedad."};

            for(int i=0;i<7;i++)
            if(alineamientos.contains(alineamientosTodos[i])){
                alis[i] = 1;
            }

            Solicitud sol = solicitudService.crearSolicitud(

                    titulo.getValue(), nombreCorto.getValue(), interesados.getValue(), financiacion.getValue().longValue(), alis[0], alis[1], alis[2],
                    alis[3], alis[4], alis[5], alis[6], alcance.getValue(), importanciaProm.getValue().intValue(),
                    usuarioRepository.findByNombre(securityService.getAuthenticatedUser().getUsername()),
                    this.memoriaBytes, this.tecnicoBytes, this.presupuestoBytes, 2);

            sol = solicitudService.asignarPromotor(sol.getId(), usuarioRepository.findByNombre(promotor.getValue()).getId());
        });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button has a more prominent look.
        enviar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        enviar.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in
        // styles.css.
        enviar.addClassName("centered-content");

        add(    titulo,
                nombreCorto,

                new H2("Información del promotor"),
                promotor,
                importanciaProm,

                new H2("Información de los interesados"),
                interesados,
                financiacion,

                new H2("Justificación del proyecto"),
                alineamiento,
                alcance,

                new H4("Memoria"),
                memoria,

                new H2("Documentación adicional"),
                new H4("Especificaciones técnicas:"),
                tecnico,
                new H4("Presupuesto(s):"),
                presupuestos,

                enviar);



        // Use custom CSS classes to apply styling. This is defined in
        // styles.css.


    }

}
