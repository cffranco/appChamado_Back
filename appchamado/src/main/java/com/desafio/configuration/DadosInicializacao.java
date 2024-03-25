package com.desafio.configuration;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.desafio.entity.Usuario;
import com.desafio.service.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


@Component
public class DadosInicializacao implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
    UsuarioRepository usuarioRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {

        List<Usuario> users = usuarioRepository.findAll();
        /*
        if (users.isEmpty()) {
            createUsuario("admin", "admin@exemplo.com",Util.criptografiaBase64Encoder("123456"),Constantes.PERFIL_ADMIN);
            createUsuario("cliente", "cliente@exemplo.com",Util.criptografiaBase64Encoder("123456"),Constantes.PERFIL_CLIENT);
        }*/
        if (users.isEmpty()) {
            createUsuario("admin", "admin@exemplo.com", passwordEncoder.encode("123456"),Constantes.PERFIL_ADMIN);
            createUsuario("cliente", "cliente@exemplo.com", passwordEncoder.encode("123456"),Constantes.PERFIL_CLIENT);
        }

    }

    public void createUsuario(String nome, String email, String senha, String perfil) {

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setPerfil(perfil);
        usuario.setSenha(senha);
        
        
        usuarioRepository.save(usuario);
    }

}
