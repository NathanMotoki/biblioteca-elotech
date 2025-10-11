package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Emprestimo;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.model.StatusEmprestimo;
import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.EmprestimoRepository;
import com.elotech.biblioteca.repository.LivroRepository;
import com.elotech.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @Test
    @DisplayName("Deve retornar uma lista de emprestimos")
    public void deveRetornarUmaListaDeEmprestimos() {
        Emprestimo emprestimo1 = new Emprestimo(
                1L,
                new Usuario(),
                new Livro(),
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                StatusEmprestimo.ATIVO
        );

        Emprestimo emprestimo2 = new Emprestimo(
                2L,
                new Usuario(),
                new Livro(),
                LocalDate.now().minusDays(3),
                LocalDate.now().plusDays(4),
                StatusEmprestimo.ATIVO
        );

        Mockito.when(emprestimoRepository.findAll()).thenReturn(List.of(emprestimo1, emprestimo2));
        List<Emprestimo> listaEmprestimos = emprestimoService.listarTodosEmprestimos();
        Assertions.assertEquals(2, listaEmprestimos.size());
    }

    @Test
    @DisplayName("Deve criar um empréstimo com sucesso")
    public void deveCriarUmEmprestimoComSucesso() {
        Usuario usuario = new Usuario(1L, "João Silva", "joao@email.com", LocalDate.of(2024, 1, 1), "999999999");
        Livro livro = new Livro(1L, "O Hobbit", "J.R.R. Tolkien", "9780547928227", LocalDate.of(1937, 9, 21), "Fantasia", null);
        
        Emprestimo emprestimoParaCriar = new Emprestimo();
        emprestimoParaCriar.setUsuario(usuario);
        emprestimoParaCriar.setLivro(livro);
        emprestimoParaCriar.setDataEmprestimo(LocalDate.of(2025, 10, 5));
        
        Emprestimo emprestimoSalvo = new Emprestimo(1L, usuario, livro, 
                LocalDate.of(2025, 10, 5), null, StatusEmprestimo.ATIVO);
        
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        Mockito.when(emprestimoRepository.findByLivroIdAndStatus(1L, StatusEmprestimo.ATIVO)).thenReturn(Optional.empty());
        Mockito.when(emprestimoRepository.save(Mockito.any(Emprestimo.class))).thenReturn(emprestimoSalvo);
        
        Emprestimo resultado = emprestimoService.criarEmprestimo(emprestimoParaCriar);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(livro, resultado.getLivro());
        assertEquals(LocalDate.of(2025, 10, 5), resultado.getDataEmprestimo());
        assertNull(resultado.getDataDevolucao());
        assertEquals(StatusEmprestimo.ATIVO, resultado.getStatus());
        
        Mockito.verify(usuarioRepository).findById(1L);
        Mockito.verify(livroRepository).findById(1L);
        Mockito.verify(emprestimoRepository).findByLivroIdAndStatus(1L, StatusEmprestimo.ATIVO);
        Mockito.verify(emprestimoRepository).save(Mockito.any(Emprestimo.class));
    }

    @Test
    @DisplayName("Retornar erro ao criar emprestimo com campos nulos")
    public void retornarErroAoCriarEmprestimoComCamposNulos() {
        Emprestimo emprestimo = new Emprestimo();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emprestimoService.criarEmprestimo(emprestimo);
        });
        Assertions.assertEquals("Todos os campos do empréstimo são obrigatórios.", exception.getMessage());
    }

}