package com.desafio.dto;

public class LoginDTO {
	
	public record LoginRequest(String email, String senha) { }
	public record Response(String message, String perfil, Long id) { }
	
}

