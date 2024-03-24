package com.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.dto.ChamadoDTO;
import com.desafio.dto.PesquisaChamadoDTO;
import com.desafio.service.ChamadoService;

@RestController
@RequestMapping("/chamado")
@CrossOrigin(origins = "http://localhost:4200/")
public class ChamadoController {
	
	@Autowired
	private ChamadoService service;

    @PostMapping("/salva")
    public ResponseEntity<ChamadoDTO> cadastrarChamado(@RequestBody ChamadoDTO chamadoDTO) {
        ChamadoDTO novoChamado = service.cadastrarChamado(chamadoDTO);
        return new ResponseEntity<>(novoChamado, HttpStatus.CREATED);
    }

    @PostMapping("/pesquisar")
    public ResponseEntity<List<ChamadoDTO>> pesquisarChamados(@RequestBody PesquisaChamadoDTO filtro) {
      List<ChamadoDTO> chamados = service.pesquisarChamadosPorCliente(filtro);
      return ResponseEntity.ok(chamados);
    }
    
    @PostMapping("/pesquisarAdmin")
    public ResponseEntity<List<ChamadoDTO>> pesquisarChamadosAdmin() {
      List<ChamadoDTO> chamados = service.pesquisarChamadosPorAdmin();
      return ResponseEntity.ok(chamados);
    }
    
    @PutMapping("/resposta")
    public ResponseEntity<ChamadoDTO> respostaChamado(@RequestBody ChamadoDTO chamadoDTO) {
        ChamadoDTO chamado = service.respostaChamado(chamadoDTO)
                .orElse(null);

        if (chamado != null) {
            return new ResponseEntity<>(chamado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/avaliar")
    public ResponseEntity<ChamadoDTO> avaliarChamado(@RequestBody ChamadoDTO chamadoDTO) {
        ChamadoDTO chamado = service.avaliarChamado(chamadoDTO)
                .orElse(null);

        if (chamado != null) {
            return new ResponseEntity<>(chamado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /*@GetMapping
    public ResponseEntity<List<ChamadoDTO>> listarChamado() {
        List<ChamadoDTO> chamado = service.listarChamados();
        return new ResponseEntity<>(chamado, HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> obterChamado(@PathVariable Long id) {
        return service.obterChamado(id)
                .map(chamado -> new ResponseEntity<>(chamado, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ChamadoDTO> atualizarChamado(@PathVariable Long id, @RequestBody ChamadoDTO chamadoDTO) {
        ChamadoDTO chamado = service.atualizarChamado(id, chamadoDTO)
                .orElse(null);

        if (chamado != null) {
            return new ResponseEntity<>(chamado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirChamado(@PathVariable Long id) {
        service.excluirChamado(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
}
