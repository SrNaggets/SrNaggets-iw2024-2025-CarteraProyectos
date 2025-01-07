package com.iw.IW.configuration;

import com.iw.IW.entities.Usuario;
import com.iw.IW.views.LoginView;
import com.iw.IW.repositories.UsuarioRepository;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration
        extends VaadinWebSecurity {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/public/**"))
                .permitAll());

        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * Demo UserDetailsManager which only provides two hardcoded
     * in memory users and their roles.
     * NOTE: This shouldn't be used in real world applications.
     */
    @Bean
    public UserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        for ( Usuario usuario : usuarioRepository.findAll()){
            UserDetails user = User.withUsername(usuario.getNombre())
                    .password(usuario.getContraseña())
                    .roles(usuario.getRole())
                    .build();
            manager.createUser(user);
        }

        return manager;


        /*
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}user")
                        .roles("USER")
                        .build();
        UserDetails admin =
                User.withUsername("admin")
                        .password("{noop}admin")
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(user, admin);
        */
    }
}