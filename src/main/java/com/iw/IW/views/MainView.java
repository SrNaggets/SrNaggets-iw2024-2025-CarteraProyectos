package com.iw.IW.views;


import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.UsuarioService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Route("")
@PageTitle("Menú principal")
@RolesAllowed({"USER", "ADMIN", "normal", "PROMOTOR", "CIO", "OTP"})
public class MainView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SecurityService securityService;

    public MainView(@Autowired SecurityService securityService, @Autowired UsuarioRepository usuarioRepository) {
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);


        Button logout = new Button("Logout", click -> securityService.logout());

        Button solicitud = new Button("Solicitud", click -> {
            getUI().ifPresent(ui -> ui.navigate("/solicitud"));
        });

        add(logout, solicitud);

        Set<String> roles = securityService.getAuthenticatedUser().getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());

        if(roles.contains("ROLE_PROMOTOR")){
            Button avalar = new Button("Avalar", click -> {
                getUI().ifPresent(ui -> ui.navigate("/avalar"));
            });
            add(avalar);
        }

        if(roles.contains("ROLE_OTP")){
            Button avalar = new Button("Evaluaciones técnicas", click -> {
                getUI().ifPresent(ui -> ui.navigate("/evaluaciontecnica"));
            });
            add(avalar);
        }

        if(roles.contains("ROLE_CIO")){
            Button avalar = new Button("Evaluaciones estratégicas", click -> {
                getUI().ifPresent(ui -> ui.navigate("/evaluacionestrategica"));
            });
            add(avalar);

            Button proyectos = new Button("Proyectos en marcha", click -> {
                getUI().ifPresent(ui -> ui.navigate("/proyectos"));
            });
            add(proyectos);
        }



    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        UserDetails user = securityService.getAuthenticatedUser();

        if(usuarioRepository.findByCorreo(user.getUsername()).isPresent()){
            if(usuarioRepository.findByCorreo(user.getUsername()).get().getVeri() == 0){
                event.rerouteTo(VerificationView.class);
            }

        }
    }
}
