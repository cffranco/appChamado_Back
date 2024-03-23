package com.desafio.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.desafio.entity.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query("Select u from Usuario u where u.email= :email")
	Usuario buscaByEmail(String email);

}
