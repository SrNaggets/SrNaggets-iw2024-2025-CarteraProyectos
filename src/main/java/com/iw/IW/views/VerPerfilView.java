package com.iw.IW.views;

import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.EvaluacionTecnicaRepository;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.SolicitudService;
import com.iw.IW.services.UsuarioService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Route("perfil")
@PageTitle("Ver perfil")
@RolesAllowed({"USER", "ADMIN", "normal", "PROMOTOR", "CIO", "OTP"})
public class VerPerfilView extends VerticalLayout implements HasUrlParameter<Long> {


    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SolicitudService solicitudService;

    @Autowired
    EvaluacionTecnicaRepository evaluacionTecnicaRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SecurityService securityService;


    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        if (usuarioRepository.findById(parameter).isPresent() &&  usuarioRepository.findById(parameter).get().getId().equals(usuarioRepository.findByCorreo(securityService.getAuthenticatedUser().getUsername()).get().getId())) {
            Usuario usuario = usuarioRepository.findById(parameter).get();
            getStyle().setBackground("#d6fdff");
            setSizeUndefined();

            Button principal = new Button("Volver a menú principal", click -> getUI().ifPresent(ui -> ui.navigate("")));
            add(principal);

            add(new H3("Correo:"), new Paragraph(usuario.getCorreo()),
                    new H3("Nombre de usuario:"), new Paragraph(usuario.getNombre()),
                    new H3("Rol:"), new Paragraph(usuario.getRole()));

            Button editar = new Button("Editar perfil", click -> getUI().ifPresent(ui -> ui.navigate("/roles/" + usuarioRepository.findByCorreo(securityService.getAuthenticatedUser().getUsername()).get().getId())));
            editar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            add(editar);

        }
        else{
            event.rerouteTo(NavegacionView.class);
        }

    }

}