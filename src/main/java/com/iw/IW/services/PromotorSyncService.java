package com.iw.IW.services;

import com.iw.IW.dto.PromotorResponse;
import com.iw.IW.entities.Usuario;
import com.iw.IW.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PromotorSyncService {

    private static final String PROMOTOR_URL = "https://e608f590-1a0b-43c5-b363-e5a883961765.mock.pstmn.io/sponsors";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void sincronizarPromotores() {
        PromotorResponse response = restTemplate.getForObject(PROMOTOR_URL, PromotorResponse.class);

        if (response != null && "success".equals(response.getStatus())) {
            List<PromotorResponse.PromotorData> promotores = response.getPromotores();

            for (PromotorResponse.PromotorData promotor : promotores) {
                String correo = promotor.getId();
                String nombre = promotor.getNombre();

                Usuario usuarioExistente = usuarioRepository.findByCorreo(correo).orElse(null);

                if (usuarioExistente == null) {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setCorreo(correo);
                    nuevoUsuario.setNombre(nombre);
                    nuevoUsuario.setContraseña(correo);
                    nuevoUsuario.setRole("PROMOTOR");
                    nuevoUsuario.setVeri(1);

                    usuarioRepository.save(nuevoUsuario);
                } else {
                    usuarioExistente.setNombre(nombre);
                    usuarioRepository.save(usuarioExistente);
                }
            }
        }
    }
}
