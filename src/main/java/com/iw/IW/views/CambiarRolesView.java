package com.iw.IW.views;

import com.iw.IW.entities.Solicitud;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.SolicitudService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("roles")
@PageTitle("Cambiar roles de usuarios")
@RolesAllowed({"CIO"})
public class CambiarRolesView extends VerticalLayout {

    public CambiarRolesView(@Autowired SecurityService securityService, @Autowired UsuarioRepository usuarioRepository){
        getStyle().setBackground("#d6fdff");
        setSizeUndefined();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.AUTO);

        Button logout = new Button("Logout", click -> securityService.logout());
        Button principal = new Button("Volver a menú principal", click -> getUI().ifPresent(ui -> ui.navigate("")));

        add(new HorizontalLayout(logout, principal));

        List<Usuario> aux = usuarioRepository.findAll();

        add(new H2("Lista de usuarios:"));

        if(aux.isEmpty()){
            add(new H4("No hay usuarios."));
        }
        else{


            Grid<Usuario> gridUsuarios = new Grid<>(Usuario.class, false);
            gridUsuarios.addColumn(new ComponentRenderer<>(p -> new Anchor("/roles/" + p.getId(), p.getNombre()))).setHeader("Título");

            gridUsuarios.addColumn(Usuario::getRole).setHeader("Rol del usuario");

            gridUsuarios.setItems(aux);

            add(gridUsuarios);
        }
    }
}
