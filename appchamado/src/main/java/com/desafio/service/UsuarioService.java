package com.desafio.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.configuration.Util;
import com.desafio.dto.UsuarioDTO;
import com.desafio.entity.Usuario;
import com.desafio.exception.ResourceNotFoundException;
import com.desafio.service.repository.UsuarioRepository;



@Service
public class UsuarioService {
	@Autowired
    private UsuarioRepository repository;
   
    @Autowired
    private ModelMapper modelMapper;
    
    public Optional<UsuarioDTO> loginUsuario(String email, String senha) {
    	Usuario usuarioExistente = repository.buscaByEmail(email);
        if (usuarioExistente != null) {
        	if(Util.criptografiaBase64Encoder(senha).equals(usuarioExistente.getSenha())) {
        		return Optional.of(modelMapper.map(usuarioExistente, UsuarioDTO.class));
        	}else {
        		throw new ResourceNotFoundException("Senha errada! ");
        	}
        	
        } else {
            throw new ResourceNotFoundException("Usuario não encontrado com o email: " + email);
        }
    }
    /*

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuario = repository.findAll();
        return usuario.stream()
                .map(usuarios -> modelMapper.map(usuarios, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> obterUsuario(Long id) {
        return repository.findById(id)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }
    
    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
    	
    	Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
    	usuario = repository.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public Optional<UsuarioDTO> atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
    	
        Optional<Usuario> usuarioExistente = repository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
            usuario.setId(id);
            usuario = repository.save(usuario);
            return Optional.of(modelMapper.map(usuario, UsuarioDTO.class));
        } else {
            throw new ResourceNotFoundException("Usuario não encontrado com o ID: " + id);
        }
    }

    public void excluirUsuario(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Usuario não encontrado com o ID: " + id);
        }
    }*/

}
