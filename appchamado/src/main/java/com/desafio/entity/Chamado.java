package com.desafio.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Chamado {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String assunto;
	private String resposta;
	private String situacao;
	private Integer avaliacao;
	private Long idCliente;
	private Long idAdmin;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Long getIdAdmin() {
		return idAdmin;
	}
	public void setIdAdmin(Long idAdmin) {
		this.idAdmin = idAdmin;
	}
	public Integer getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(Integer avaliacao) {
		this.avaliacao = avaliacao;
	}
	@Override
	public int hashCode() {
		return Objects.hash(assunto, avaliacao, id, idAdmin, idCliente, resposta, situacao);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamado other = (Chamado) obj;
		return Objects.equals(assunto, other.assunto) && Objects.equals(avaliacao, other.avaliacao)
				&& Objects.equals(id, other.id) && Objects.equals(idAdmin, other.idAdmin)
				&& Objects.equals(idCliente, other.idCliente) && Objects.equals(resposta, other.resposta)
				&& Objects.equals(situacao, other.situacao);
	}
	
	
}
