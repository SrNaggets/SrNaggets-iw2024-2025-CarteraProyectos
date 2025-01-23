package com.iw.IW.views;


import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.UsuarioService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
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

import static javax.swing.UIManager.getUI;

@Route("/menuprincipal")
@PageTitle("Menú principal")
@RolesAllowed({"USER", "ADMIN", "normal", "PROMOTOR", "CIO", "OTP"})
public class NavegacionView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SecurityService securityService;

    public NavegacionView(@Autowired SecurityService securityService, @Autowired UsuarioRepository usuarioRepository) {
        //setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(Alignment.AUTO);
        getStyle().setBackground("url('https://files.catbox.moe/iall3h.png')");
        setHeight("100%");
        setWidth("100%");



        Set<String> roles = securityService.getAuthenticatedUser().getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());

        MenuBar menu = new MenuBar();
        Button logout = new Button("Cerrar sesión", click -> securityService.logout());
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button ver = new Button("Ver perfil", click -> getUI().ifPresent(ui -> ui.navigate("/perfil/" + usuarioRepository.findByCorreo(securityService.getAuthenticatedUser().getUsername()).get().getId())));
        ver.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        menu.addItem("Crear solicitud", e -> getUI().ifPresent(ui -> ui.navigate("/solicitud")));


        if(roles.contains("ROLE_PROMOTOR")){
            menu.addItem("Avalar solicitudes", click ->
                    getUI().ifPresent(ui -> ui.navigate("/avalar")));

        }

        if(roles.contains("ROLE_OTP")){
            menu.addItem("Realizar evaluación técnica", click ->
                    getUI().ifPresent(ui -> ui.navigate("/evaluaciontecnica")));

        }

        if(roles.contains("ROLE_CIO")){

            MenuItem accionesCIO = menu.addItem("Acciones CIO");

            SubMenu submenuCIO = accionesCIO.getSubMenu();
            submenuCIO.addItem("Realizar evaluación estratégica", click ->
                    getUI().ifPresent(ui -> ui.navigate("/evaluacionestrategica")));

            submenuCIO.addItem("Proyectos en marcha", click ->
                    getUI().ifPresent(ui -> ui.navigate("/proyectos")));

            MenuItem adminCIO = submenuCIO.addItem("Administrar proyectos");
            SubMenu subMenuAdmin = adminCIO.getSubMenu();

            subMenuAdmin.addItem("Priorizar proyectos", click ->
                    getUI().ifPresent(ui -> ui.navigate("/priorizar")));

            subMenuAdmin.addItem("Ejecutar proyectos", click ->
                    getUI().ifPresent(ui -> ui.navigate("/ejecutar")));

            subMenuAdmin.addItem("Finalizar/cancelar proyectos", click ->
                    getUI().ifPresent(ui -> ui.navigate("/finalizar")));

            menu.addItem("Administrar usuarios", click ->
                    getUI().ifPresent(ui -> ui.navigate("/roles")));

        }

        menu.addItem("Ver cartera de proyectos", click ->
                getUI().ifPresent(ui -> ui.navigate("/carteraproyectos")));

        menu.addItem("Ver mis solicitudes", click ->
                getUI().ifPresent(ui -> ui.navigate("/missolicitudes")));



        add(new HorizontalLayout(menu, ver, logout));

        String nombreRol = roles.stream().findFirst().get().substring(5);
        if(!nombreRol.equals("CIO") && !nombreRol.equals("OTP")){
            nombreRol = nombreRol.toLowerCase();
        }
        if(nombreRol.equals("normal")) nombreRol = "usuario";

        add(new H4("Bienvenido, " + nombreRol + " " + usuarioRepository.findByCorreo(securityService.getAuthenticatedUser().getUsername()).get().getNombre()));

        /*
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

            Button priorizar = new Button("Priorizar proyectos", click -> {
                getUI().ifPresent(ui -> ui.navigate("/priorizar"));
            });
            add(priorizar);

            Button ejecutar = new Button("Ejecutar proyectos", click -> {
                getUI().ifPresent(ui -> ui.navigate("/ejecutar"));
            });
            add(ejecutar);

            Button finalizar = new Button("Finalizar/cancelar proyectos", click -> {
                getUI().ifPresent(ui -> ui.navigate("/finalizar"));
            });
            add(finalizar);

            Button roless = new Button("Cambiar roles", click -> {
                getUI().ifPresent(ui -> ui.navigate("/roles"));
            });
            add(roless);
        }

        add(new Button("Ver cartera de proyectos", e -> {
            getUI().ifPresent(ui -> ui.navigate("/carteraproyectos"));
        }));

        add(new Button("Ver mis solicitudes", e -> {
            getUI().ifPresent(ui -> ui.navigate("/missolicitudes"));
        }));*/



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
