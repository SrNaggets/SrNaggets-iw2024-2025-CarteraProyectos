package com.iw.IW.views;

import com.iw.IW.entities.Usuario;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.iw.IW.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

@AnonymousAllowed
@PageTitle("Registro de usuario")
@Route("register")
public class RegisterView extends VerticalLayout {

    @Autowired
    UsuarioService usuarioService;

    public RegisterView(){

        TextField usernameField = new TextField("Nombre de usuario");
        PasswordField passwordField = new PasswordField("Contraseña");
        PasswordField confirmPasswordField = new PasswordField("Confirmar contraseña");
        TextField emailField = new TextField("Correo electrónico");
        Checkbox adminRoleCheckBox = new Checkbox("Rol de administrador");

        // Botón de registro
        Button registerButton = new Button("Registrar");

        // Agregar acción al botón
        registerButton.addClickListener(event -> {
            // Obtener los valores de los campos
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();
            String email = emailField.getValue();

            // Validar que la contraseña y la confirmación coinciden
            if (!password.equals(confirmPassword)) {
                Notification.show("Las contraseñas no coinciden", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            // Crear el usuario (podemos usar el servicio para manejar la lógica de negocio)
            Usuario success = usuarioService.registrarUsuario(email, username, password);

            if (success.getNombre().equals(username)) {
                getUI().ifPresent(ui -> ui.navigate("/login"));
            } else {
                Notification.show("Hubo un error al registrar el usuario", 3000, Notification.Position.MIDDLE);
            }
        });

        // Crear el formulario y agregar los componentes
        FormLayout formLayout = new FormLayout();
        formLayout.add(usernameField, passwordField, confirmPasswordField, emailField);

        // Agregar los componentes a la vista
        add(formLayout, registerButton);

        Button login = new Button("Volver a iniciar sesión", click -> {
            getUI().ifPresent(ui -> ui.navigate("/login"));
        });
        add(login);

    }

}
