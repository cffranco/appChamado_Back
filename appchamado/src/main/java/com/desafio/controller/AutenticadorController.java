package com.desafio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.dto.AutenticacaoDTO;
import com.desafio.entity.Usuario;
import com.desafio.service.AutenticacaoService;
import com.desafio.service.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/auth")
@Validated
@CrossOrigin(origins = "http://localhost:4200/")
public class AutenticadorController {
	private static final Logger log = LoggerFactory.getLogger(AutenticadorController.class);

    @Autowired
    private AutenticacaoService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    
	@Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AutenticacaoDTO.LoginRequest userLogin) throws IllegalAccessException {
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                userLogin.email(),
                                userLogin.senha()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Token requested for user :{}", authentication.getAuthorities());
        String token = authService.generateToken(authentication);
        Usuario usuario = usuarioRepository.buscaByEmail(userLogin.email());
        
       // AutenticacaoUsuario userDetails = (AutenticacaoUsuario) authentication.getPrincipal();
        
        AutenticacaoDTO.Response response = new AutenticacaoDTO.Response("User logged in successfully", token, usuario.getPerfil(), usuario.getId());

        return ResponseEntity.ok(response);
    }
}
