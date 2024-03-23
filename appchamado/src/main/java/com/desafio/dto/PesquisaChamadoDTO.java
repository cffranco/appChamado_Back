package com.desafio.dto;

public class PesquisaChamadoDTO {
	private String idCliente;
	private String assunto;
	private Long idChamado;
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public Long getIdChamado() {
		return idChamado;
	}
	public void setIdChamado(Long idChamado) {
		this.idChamado = idChamado;
	}

}
