package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.UsuarioRepository;
import com.elotech.biblioteca.validator.UsuarioValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioValidator usuarioValidator;

    @Test
    @DisplayName("Deve retornar uma lista com um usuário")
    public void deveRetornarUmaListaComUmUsuario() {
        Usuario usuario = new Usuario(1L, "João Silva", "joao.silva@hotmail.com", LocalDate.of(2025, 1, 1), "999999999");
        Mockito.when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();

        Assertions.assertEquals(1, usuarios.size());
    }

    @Test
    @DisplayName("Deve retornar erro ao criar usuário com email inválido")
    public void deveRetornarErroAoCriarUsuarioComEmailInvalido() {
        Usuario usuario = new Usuario(1L, "João Silva", "joao.silva", LocalDate.of(2025, 1, 1), "999999999");

        Mockito.doThrow(new IllegalArgumentException("O formato do e-mail é inválido."))
                .when(usuarioValidator).validarUsuarioParaCriacao(usuario);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.criarUsuario(usuario);
        });

        Assertions.assertEquals("O formato do e-mail é inválido.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar erro ao salvar usuário com data de cadastro futura")
    public void deveRetornarErroAoSalvarUsuarioComDataDeCadastroFutura() {
        Usuario usuario = new Usuario(1L, "João Silva", "joao.silva@hotmail.com", LocalDate.of(2025, 2, 1), "999999999");
        
        Mockito.doThrow(new IllegalArgumentException("A data de cadastro não pode ser maior que o dia atual."))
                .when(usuarioValidator).validarUsuarioParaCriacao(usuario);
        
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.criarUsuario(usuario);
        });
        
        Assertions.assertEquals("A data de cadastro não pode ser maior que o dia atual.", exception.getMessage());
    }
}