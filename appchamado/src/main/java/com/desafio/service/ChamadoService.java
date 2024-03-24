package com.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.dto.ChamadoDTO;
import com.desafio.dto.PesquisaChamadoDTO;
import com.desafio.entity.Chamado;
import com.desafio.exception.ResourceNotFoundException;
import com.desafio.service.repository.ChamadoRepository;



@Service
public class ChamadoService {
	@Autowired
    private ChamadoRepository repository;

   // @Autowired
   // private ProfissaoService profissaoService;

    @Autowired
    private ModelMapper modelMapper;

    public List<ChamadoDTO> listarChamados() {
        List<Chamado> chamado = repository.findAll();
        return chamado.stream()
                .map(chamados -> modelMapper.map(chamados, ChamadoDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ChamadoDTO> obterChamado(Long id) {
        return repository.findById(id)
                .map(chamado -> modelMapper.map(chamado, ChamadoDTO.class));
    }

    public ChamadoDTO cadastrarChamado(ChamadoDTO chamadoDTO) {
    	chamadoDTO.setSituacao("A");
    	Chamado chamado = modelMapper.map(chamadoDTO, Chamado.class);
    	chamado = repository.save(chamado);
        return modelMapper.map(chamado, ChamadoDTO.class);
    }

    public Optional<ChamadoDTO> atualizarChamado(Long id, ChamadoDTO chamadoDTO) {
    	
        Optional<Chamado> chamadoExistente = repository.findById(id);
        if (chamadoExistente.isPresent()) {
            Chamado chamado = modelMapper.map(chamadoDTO, Chamado.class);
            chamado.setId(id);
            chamado = repository.save(chamado);
            return Optional.of(modelMapper.map(chamado, ChamadoDTO.class));
        } else {
            throw new ResourceNotFoundException("Chamado não encontrado com o ID: " + id);
        }
    }

    public void excluirChamado(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Chamado não encontrado com o ID: " + id);
        }
    }

    public List<ChamadoDTO> pesquisarChamadosPorCliente(PesquisaChamadoDTO filtro) {
        List<Chamado> chamado = new ArrayList<>();
        Long idCliente = Long.parseLong(filtro.getIdCliente());
        if (!filtro.getIdCliente().isEmpty()  && filtro.getAssunto().isEmpty() && filtro.getIdChamado()==null ) {
          chamado = repository.pesquisaPorCliente(idCliente);
        } else if (!filtro.getAssunto().isEmpty()) {
          chamado = repository.pesquisaPorAssunto(idCliente,"%".concat(filtro.getAssunto()).concat("%"));
        } else if (filtro.getIdChamado()>0) {
          chamado = repository.pesquisaPorChamado(idCliente,filtro.getIdChamado());
        } 
        
        return chamado.stream()
                .map(chamados -> modelMapper.map(chamados, ChamadoDTO.class))
                .collect(Collectors.toList());
        
      }
    
    public List<ChamadoDTO> pesquisarChamadosPorAdmin() {
        List<Chamado> chamado = new ArrayList<>();
        chamado = repository.pesquisaPorAdmin();
        return chamado.stream()
                .map(chamados -> modelMapper.map(chamados, ChamadoDTO.class))
                .collect(Collectors.toList());
        
      }
    
    public Optional<ChamadoDTO> respostaChamado(ChamadoDTO chamadoDTO) {
    	Long id = chamadoDTO.getId();
    	String resposta = chamadoDTO.getResposta();
    	Long idAdmin = chamadoDTO.getIdAdmin();
        Optional<Chamado> chamadoExistente = repository.findById(id);
        if (chamadoExistente.isPresent()) {
            Chamado chamado = chamadoExistente.get();
            chamado.setId(id);
            chamado.setResposta(resposta);
            chamado.setSituacao("F");
            chamado.setIdAdmin(idAdmin);
            chamado = repository.save(chamado);
            return Optional.of(modelMapper.map(chamado, ChamadoDTO.class));
        } else {
            throw new ResourceNotFoundException("Chamado não encontrado com o ID: " + id);
        }
    }
    
    public Optional<ChamadoDTO> avaliarChamado(ChamadoDTO chamadoDTO) {
    	Long id = chamadoDTO.getId();
    	Integer nota = chamadoDTO.getAvaliacao();
    	if(nota>10) {
    		throw new ResourceNotFoundException("A nota não pode ser mair que 10");
    	}
        Optional<Chamado> chamadoExistente = repository.findById(id);
        if (chamadoExistente.isPresent()) {
            Chamado chamado = chamadoExistente.get();
            chamado.setId(id);
            chamado.setAvaliacao(nota);
            chamado = repository.save(chamado);
            return Optional.of(modelMapper.map(chamado, ChamadoDTO.class));
        } else {
            throw new ResourceNotFoundException("Chamado não encontrado com o ID: " + id);
        }
    }
}
