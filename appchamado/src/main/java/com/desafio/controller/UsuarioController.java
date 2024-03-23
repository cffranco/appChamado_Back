package com.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desafio.dto.LoginDTO;
import com.desafio.dto.UsuarioDTO;
import com.desafio.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200/")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuario() {
        List<UsuarioDTO> usuario = service.listarUsuarios();
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id) {
        return service.obterUsuario(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO.LoginRequest userLogin) {
		return service.loginUsuario(userLogin.email(), userLogin.senha())
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO novoUsuario = service.cadastrarUsuario(usuarioDTO);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuario = service.atualizarUsuario(id, usuarioDTO)
                .orElse(null);

        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        service.excluirUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
