package appchamado;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.desafio.dto.UsuarioDTO;
import com.desafio.entity.Usuario;
import com.desafio.exception.ResourceNotFoundException;
import com.desafio.service.UsuarioService;
import com.desafio.service.repository.UsuarioRepository;

import com.desafio.configuration.Util;


class UsuarioServiceTest {

	@Mock
    private UsuarioRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginUsuario_UsuarioExistente_SenhaCorreta() {
        String email = "test@example.com";
        String senha = "senha";
        String senhaCriptografada = Util.criptografiaBase64Encoder(senha);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail(email);
        usuarioExistente.setSenha(senhaCriptografada);

        when(repository.buscaByEmail(email)).thenReturn(usuarioExistente);
        when(modelMapper.map(usuarioExistente, UsuarioDTO.class)).thenReturn(new UsuarioDTO());

        Optional<UsuarioDTO> resultDTO = usuarioService.loginUsuario(email, senha);

        assertNotNull(resultDTO);
        assertTrue(resultDTO.isPresent());

        verify(repository, times(1)).buscaByEmail(email);
        verify(modelMapper, times(1)).map(usuarioExistente, UsuarioDTO.class);
    }

    @Test
    public void testLoginUsuario_UsuarioExistente_SenhaIncorreta() {
        String email = "test@example.com";
        String senha = "senha";
        String senhaCriptografada = Util.criptografiaBase64Encoder(senha);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail(email);
        usuarioExistente.setSenha(senhaCriptografada);

        when(repository.buscaByEmail(email)).thenReturn(usuarioExistente);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.loginUsuario(email, "senhaErrada"));

        verify(repository, times(1)).buscaByEmail(email);
    }

    @Test
    public void testLoginUsuario_UsuarioNaoExistente() {
        String email = "test@example.com";

        when(repository.buscaByEmail(email)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.loginUsuario(email, "senha"));

        verify(repository, times(1)).buscaByEmail(email);
    }

}
