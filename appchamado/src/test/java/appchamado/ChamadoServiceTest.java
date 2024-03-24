package appchamado;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.desafio.dto.ChamadoDTO;
import com.desafio.dto.PesquisaChamadoDTO;
import com.desafio.entity.Chamado;
import com.desafio.exception.ResourceNotFoundException;
import com.desafio.service.ChamadoService;
import com.desafio.service.repository.ChamadoRepository;

public class ChamadoServiceTest {

    @Mock
    private ChamadoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ChamadoService chamadoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarChamado_NullInput() {
        assertThrows(NullPointerException.class, () -> chamadoService.cadastrarChamado(null));
    }

    @Test
    public void testCadastrarChamado_EmptyAssunto() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        chamadoDTO.setAssunto("");
        assertThrows(ResourceNotFoundException.class, () -> chamadoService.cadastrarChamado(chamadoDTO));
    }

    public void testCadastrarChamado_ValidInput() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        chamadoDTO.setAssunto("Assunto válido");

        Chamado chamado = new Chamado();
        when(modelMapper.map(chamadoDTO, Chamado.class)).thenReturn(chamado);
        when(repository.save(chamado)).thenReturn(chamado);

        ChamadoDTO resultDTO = chamadoService.cadastrarChamado(chamadoDTO);

        assertNotNull(resultDTO);
        assertEquals("A", resultDTO.getSituacao());
        verify(modelMapper, times(1)).map(chamadoDTO, Chamado.class);
        verify(repository, times(1)).save(chamado);
    }
    
    @Test
    public void testPesquisarChamadosPorCliente_NullInput() {
        assertThrows(NullPointerException.class, () -> chamadoService.pesquisarChamadosPorCliente(null));
    }

    @Test
    public void testPesquisarChamadosPorCliente_IdClienteEmpty() {
        PesquisaChamadoDTO filtro = new PesquisaChamadoDTO();
        filtro.setIdCliente("");

        assertDoesNotThrow(() -> chamadoService.pesquisarChamadosPorCliente(filtro));
    }

    @Test
    public void testPesquisarChamadosPorCliente_AssuntoNotEmpty() {
        PesquisaChamadoDTO filtro = new PesquisaChamadoDTO();
        filtro.setIdCliente("1");
        filtro.setAssunto("Assunto");

        assertDoesNotThrow(() -> chamadoService.pesquisarChamadosPorCliente(filtro));
    }

    @Test
    public void testPesquisarChamadosPorCliente_IdChamadoNotEmpty() {
        PesquisaChamadoDTO filtro = new PesquisaChamadoDTO();
        filtro.setIdCliente("1");
        filtro.setIdChamado(1L);

        assertDoesNotThrow(() -> chamadoService.pesquisarChamadosPorCliente(filtro));
    }
    
    @Test
    public void testPesquisarChamadosPorAdmin() {
        
        List<Chamado> chamadosSimulados = new ArrayList<>();
        chamadosSimulados.add(new Chamado());
        chamadosSimulados.add(new Chamado());
        when(repository.pesquisaPorAdmin()).thenReturn(chamadosSimulados);
        when(modelMapper.map(any(Chamado.class), eq(ChamadoDTO.class)))
                .thenAnswer(invocation -> {
                    Chamado chamado = invocation.getArgument(0);
                    return new ChamadoDTO(); 
                });

        List<ChamadoDTO> result = chamadoService.pesquisarChamadosPorAdmin();
        assertNotNull(result);
        assertEquals(chamadosSimulados.size(), result.size());
        verify(repository, times(1)).pesquisaPorAdmin();
        verify(modelMapper, times(chamadosSimulados.size())).map(any(Chamado.class), eq(ChamadoDTO.class));
    }
    
    @Test
    public void testRespostaChamado_NullInput() {
        assertThrows(NullPointerException.class, () -> chamadoService.respostaChamado(null));
    }

    @Test
    public void testRespostaChamado_EmptyResposta() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        chamadoDTO.setId(1L);
        chamadoDTO.setResposta("");

        assertThrows(ResourceNotFoundException.class, () -> chamadoService.respostaChamado(chamadoDTO));
    }

    @Test
    public void testRespostaChamado_ChamadoNotFound() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        chamadoDTO.setId(1L);
        chamadoDTO.setResposta("Resposta válida");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> chamadoService.respostaChamado(chamadoDTO));
    }

    @Test
    public void testRespostaChamado_ValidInput() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        chamadoDTO.setId(1L);
        chamadoDTO.setResposta("Resposta válida");
        chamadoDTO.setIdAdmin(1L);

        Chamado chamado = new Chamado();
        when(repository.findById(1L)).thenReturn(Optional.of(chamado));
        when(repository.save(any(Chamado.class))).thenReturn(chamado);
        when(modelMapper.map(any(Chamado.class), eq(ChamadoDTO.class))).thenReturn(chamadoDTO);

        Optional<ChamadoDTO> resultDTO = chamadoService.respostaChamado(chamadoDTO);

        assertNotNull(resultDTO);
        assertTrue(resultDTO.isPresent());
        assertEquals(chamadoDTO.getId(), resultDTO.get().getId());
        assertEquals(chamadoDTO.getResposta(), resultDTO.get().getResposta());
        assertEquals(chamadoDTO.getIdAdmin(), resultDTO.get().getIdAdmin());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Chamado.class));
        verify(modelMapper, times(1)).map(any(Chamado.class), eq(ChamadoDTO.class));
    }
    
    @Test
    public void testAvaliarChamado_NullInput() {
        assertThrows(NullPointerException.class, () -> chamadoService.avaliarChamado(null));
    }

    @Test
    public void testAvaliarChamado_InvalidNota() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        chamadoDTO.setId(1L);
        chamadoDTO.setAvaliacao(11); // Nota maior que 10

        assertThrows(ResourceNotFoundException.class, () -> chamadoService.avaliarChamado(chamadoDTO));
    }

    @Test
    public void testAvaliarChamado_ChamadoNotFound() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        chamadoDTO.setId(1L);
        chamadoDTO.setAvaliacao(8); // Nota válida

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> chamadoService.avaliarChamado(chamadoDTO));
    }

    @Test
    public void testAvaliarChamado_ValidInput() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        chamadoDTO.setId(1L);
        chamadoDTO.setAvaliacao(8); // Nota válida

        Chamado chamado = new Chamado();
        when(repository.findById(1L)).thenReturn(Optional.of(chamado));
        when(repository.save(any(Chamado.class))).thenReturn(chamado);
        when(modelMapper.map(any(Chamado.class), eq(ChamadoDTO.class))).thenReturn(chamadoDTO);

        Optional<ChamadoDTO> resultDTO = chamadoService.avaliarChamado(chamadoDTO);

        assertNotNull(resultDTO);
        assertTrue(resultDTO.isPresent());
        assertEquals(chamadoDTO.getId(), resultDTO.get().getId());
        assertEquals(chamadoDTO.getAvaliacao(), resultDTO.get().getAvaliacao());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Chamado.class));
        verify(modelMapper, times(1)).map(any(Chamado.class), eq(ChamadoDTO.class));
    }
}