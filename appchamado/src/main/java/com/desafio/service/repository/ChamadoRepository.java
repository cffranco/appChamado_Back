package com.desafio.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafio.entity.Chamado;


public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
	@Query("Select c from Chamado c where c.situacao= 'A'")
	List<Chamado> findChamadoAbertos();
	
	@Query("Select c from Chamado c where c.idCliente= :idCliente")
	List<Chamado> pesquisaPorCliente(Long idCliente);

	@Query("Select c from Chamado c where c.idCliente= :idCliente and c.assunto= :assunto")
	List<Chamado> pesquisaPorAssunto(Long idCliente, String assunto);
	
	@Query("Select c from Chamado c where c.id= :idChamado and c.idCliente= :idCliente")
	List<Chamado> pesquisaPorChamado(Long idCliente, Long idChamado);
	
	@Query("Select c from Chamado c where c.situacao='A'")
	List<Chamado> pesquisaPorAdmin();
}
