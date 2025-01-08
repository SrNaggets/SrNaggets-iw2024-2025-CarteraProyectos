package com.iw.IW.views;

import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
import com.iw.IW.services.SecurityService;
import com.iw.IW.services.UsuarioService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@AnonymousAllowed
@PageTitle("Verificación")
@Route("verify")
public class VerificationView extends VerticalLayout {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    public VerificationView(@Autowired SecurityService securityService, @Autowired UsuarioRepository usuarioRepository){

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        if (securityService.getAuthenticatedUser() != null) {
            String correo = securityService.getAuthenticatedUser().getUsername();

            TextField campoCodigo = new TextField("Código de verificación: ");

            Button reenviar = new Button("Reenviar código");

            reenviar.addClickListener(e -> {
                usuarioService.reenviarCorreo(correo, usuarioRepository.findByCorreo(correo).get().getVerificacion());
            });

            Button submit = new Button("Enviar");

            submit.addClickListener(event -> {
                String codigo = campoCodigo.getValue();

                usuarioService.verificarUsuario(correo, codigo);

                Optional<Usuario> aux = usuarioRepository.findByCorreo(correo);

                if(aux.isPresent()){
                    Usuario usuario = aux.get();
                    if(usuario.getVeri() == 1){


                        getUI().ifPresent(ui -> ui.navigate("/"));
                    }
                    else{
                        Notification.show("ERROR: Usuario no verificado", 3000, Notification.Position.MIDDLE)
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                }
                else{
                    Notification.show("ERROR: No se encontró el correo", 3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });

            add(campoCodigo, submit, reenviar);
        }
        else{
            getUI().ifPresent(ui -> ui.navigate("/login"));
        }




    }
}
