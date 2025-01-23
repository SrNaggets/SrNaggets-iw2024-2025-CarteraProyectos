package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.SolicitudRepository;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.SolicitudService;
import com.iw.IW.services.UsuarioService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.List;

@Route("carteraproyectos")
@PageTitle("Ver cartera de proyectos")
@RolesAllowed({"USER", "ADMIN", "normal", "PROMOTOR", "CIO", "OTP"})
public class VerCarteraProyectosView extends VerticalLayout {

    public VerCarteraProyectosView(@Autowired SecurityService securityService, @Autowired UsuarioRepository usuarioRepository, @Autowired SolicitudRepository solicitudRepository, @Autowired SolicitudService solicitudService){
        //setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.AUTO);

        getStyle().setBackground("url('https://files.catbox.moe/rdghw1.png')");
        setHeight("100%");
        setWidth("100%");

        Usuario usuario = usuarioRepository.findByCorreo(securityService.getAuthenticatedUser().getUsername()).get();

        Button logout = new Button("Logout", click -> securityService.logout());

        Button principal = new Button("Volver a menú principal", click -> getUI().ifPresent(ui -> ui.navigate("")));

        add(new HorizontalLayout(logout, principal));

        List<Solicitud> aux = solicitudRepository.findByEstado("en ejecución");

        add(new H2("Cartera de proyectos:"));

        if(aux.isEmpty()){
            add(new H4("No hay proyectos en ejecución."));
        }
        else{

            Grid<Solicitud> gridSolicitudes = new Grid<>(Solicitud.class, false);
            gridSolicitudes.addColumn(Solicitud::getTitulo).setHeader("Título");
            gridSolicitudes.addColumn(Solicitud::getInteresados).setHeader("Interesados");
            gridSolicitudes.addColumn(Solicitud::getEstado).setHeader("Estado");
            gridSolicitudes.addColumn(e -> e.getUsuario().getNombre()).setHeader("Solicitante");
            gridSolicitudes.addColumn(e -> e.getPromotor().getNombre()).setHeader("Promotor");
            gridSolicitudes.addColumn(Solicitud::getPrioridad).setHeader("Prioridad");

            gridSolicitudes.setItems(aux);

            MenuBar opciones = new MenuBar();



            opciones.addItem("Fecha", e -> {
                gridSolicitudes.setItems(solicitudService.visualizarCartera(usuario.getId(), "fecha"));
            });
            opciones.addItem("Importancia", e -> {
                gridSolicitudes.setItems(solicitudService.visualizarCartera(usuario.getId(), "importancia"));
            });
            opciones.addItem("Estado", e -> {
                gridSolicitudes.setItems(solicitudService.visualizarCartera(usuario.getId(), "estado"));
            });

            add(gridSolicitudes, opciones);
        }
    }
}
