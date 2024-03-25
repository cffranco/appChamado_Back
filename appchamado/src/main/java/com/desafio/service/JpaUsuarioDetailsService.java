package com.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.desafio.entity.Usuario;
import com.desafio.service.repository.UsuarioRepository;

@Service
public class JpaUsuarioDetailsService implements UserDetailsService {

	@Autowired
    private UsuarioRepository usuarioRepository;
 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.buscaByEmail(email);
        		 if (usuario == null) {
        	            throw new UsernameNotFoundException("Usuário não encontrado: " + email);
        	        }
        		
        return org.springframework.security.core.userdetails.User.withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getPerfil()) 
                .build();
    }
    
    
}
