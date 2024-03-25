package com.desafio.dto;

public class AutenticacaoDTO {

	public record LoginRequest(String email, String senha) {
    }

    public record Response(String message, String token, String perfil, Long id) {
    }
	 
}
