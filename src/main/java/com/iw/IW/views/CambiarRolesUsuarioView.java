package com.iw.IW.views;

import com.iw.IW.entities.EvaluacionEstrategica;
import com.iw.IW.entities.EvaluacionTecnica;
import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.EvaluacionEstrategicaRepository;
import com.iw.IW.repositories.EvaluacionTecnicaRepository;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.EvaluacionEstrategicaService;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.SolicitudService;
import com.iw.IW.services.UsuarioService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route("roles")
@PageTitle("Editar usuario")
@RolesAllowed({"USER", "ADMIN", "normal", "PROMOTOR", "CIO", "OTP"})
public class CambiarRolesUsuarioView extends VerticalLayout implements HasUrlParameter<Long> {


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
        if (usuarioRepository.findById(parameter).isPresent()) {
            Usuario usuario = usuarioRepository.findById(parameter).get();

            UserDetails aux = securityService.getAuthenticatedUser();

            Usuario auxUsuario = usuarioRepository.findByCorreo(aux.getUsername()).get();

            Set<String> rolesLista = aux.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toSet());

            if(rolesLista.contains("ROLE_CIO") || auxUsuario.getId().equals(usuario.getId())){
                if(rolesLista.contains("ROLE_CIO") && !usuario.getId().equals(auxUsuario.getId())){
                    Button volver = new Button("Volver a usuarios");
                    volver.addClickListener(e -> {
                        getUI().ifPresent(ui -> ui.navigate("/roles/"));
                    });
                    add(volver);
                }
                else{
                    Button volver = new Button("Volver a menú principal");
                    volver.addClickListener(e -> {
                        getUI().ifPresent(ui -> ui.navigate("/menuprincipal/"));
                    });
                    add(volver);
                }

                TextField nombreUsuario = new TextField("Nombre de usuario:");
                nombreUsuario.addClassName("bordered");
                nombreUsuario.setPlaceholder("Usuario");
                nombreUsuario.setWidth("50%");

                TextField contraseñaUsuario = new TextField("Nueva contraseña:");
                nombreUsuario.addClassName("bordered");
                nombreUsuario.setPlaceholder("");
                nombreUsuario.setWidth("50%");

                add(nombreUsuario, contraseñaUsuario);

                if(rolesLista.contains("ROLE_CIO") && !usuario.getId().equals(auxUsuario.getId())){
                    add(new H2("Rol de " + usuario.getNombre() + ":"));

                    RadioButtonGroup<String> roles = new RadioButtonGroup<>();

                    roles.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
                    roles.setLabel("Roles para elegir: ");
                    roles.setItems("normal", "OTP", "PROMOTOR");

                    add(roles);

                    Button enviar = new Button("Actualizar usuario", e -> {

                        usuarioService.modificarUsuario(usuario.getId(), nombreUsuario.getValue(), contraseñaUsuario.getValue());
                        usuarioService.cambiarRolUsuario(usuario.getCorreo(), roles.getValue(), usuarioRepository.findByCorreo(securityService.getAuthenticatedUser().getUsername()).get().getId());

                        Notification.show("Actualización de usuario con éxito", 3000, Notification.Position.TOP_CENTER)
                                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    });


                    add(enviar);
                }
                else{
                    Button enviar = new Button("Actualizar usuario", e -> {

                        usuarioService.modificarUsuario(usuario.getId(), nombreUsuario.getValue(), contraseñaUsuario.getValue());

                        Notification.show("Datos actualizados", 3000, Notification.Position.TOP_CENTER)
                                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    });

                    add(enviar);
                }





            }
            else{
                event.rerouteTo(NavegacionView.class);
            }





        } else {
            throw new RuntimeException("Error: no se encontró solicitud");
        }

    }

}
