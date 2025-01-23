package com.iw.IW.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.Cookie;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        getStyle().setBackground("url('https://files.catbox.moe/gdf7rl.png')");

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);


        // Crear un objeto LoginI18n para manejar la internacionalización
        LoginI18n i18n = LoginI18n.createDefault();

        // Modificar los textos predeterminados


        i18n.getForm().setTitle("Inicia sesión para continuar:");
        i18n.getForm().setUsername("Correo");
        i18n.getForm().setPassword("Contraseña");
        i18n.getForm().setForgotPassword("¿Olvidaste tu contraseña?");
        i18n.getForm().setSubmit("Iniciar sesión");
        i18n.getErrorMessage().setTitle("Error de inicio de sesión");
        i18n.getErrorMessage().setMessage("Usuario o contraseña incorrectos.");

        login.setI18n(i18n);

        login.addForgotPasswordListener(f -> {
            UI.getCurrent().navigate("/forgotpassword");
        });

        login.setAction("login");

        Button registro = new Button("Crear nueva cuenta", e -> {
            UI.getCurrent().navigate("/register");
        });

        add(new H1("Cartera de proyectos de IW"), login, registro);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
