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
import com.vaadin.flow.component.html.H2;
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
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Route("")
@PageTitle("Bienvenido a la cartera de proyectos")
@AnonymousAllowed
public class MainView extends VerticalLayout implements BeforeEnterObserver{

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SecurityService securityService;

    public MainView(@Autowired SecurityService securityService, @Autowired UsuarioRepository usuarioRepository){

        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        getStyle().setBackground("url('https://files.catbox.moe/dcwszt.png')");
        setHeight("100%");
        setWidth("100%");
        Button entrar = new Button("Acceder", click ->
                getUI().ifPresent(ui -> ui.navigate("/login")));

        entrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(new H2("Bienvenido a la cartera de proyectos tecnológicos"), entrar);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        if(securityService.getAuthenticatedUser() != null){
            event.rerouteTo(NavegacionView.class);
        }
    }


}
