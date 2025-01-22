package com.iw.IW.views;

import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.UsuarioService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;


@Route("forgotpassword")
@PageTitle("Contraseña olvidada")
@AnonymousAllowed
public class OlvidaContraseñaView extends VerticalLayout {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    public OlvidaContraseñaView(){

        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);

        Button vuelta = new Button("Volver a inicio de sesión", click -> {
            getUI().ifPresent(ui -> ui.navigate("/login"));
        });

        TextField fieldCorreo = new TextField("Correo electrónico:");

        Button mandar = new Button("Enviar", e -> {

            if(usuarioRepository.findByCorreo(fieldCorreo.getValue()).isPresent()){

                usuarioService.reiniciarContraseña(fieldCorreo.getValue());
                Notification.show("Contraseña reiniciada con éxito, mira en tu correo", 5000, Notification.Position.MIDDLE);

            }
        });

        add(vuelta, fieldCorreo, mandar);
    }
}
