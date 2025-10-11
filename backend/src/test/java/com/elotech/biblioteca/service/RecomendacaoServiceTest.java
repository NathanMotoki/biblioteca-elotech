package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Emprestimo;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.EmprestimoRepository;
import com.elotech.biblioteca.repository.LivroRepository;
import com.elotech.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecomendacaoServiceTest {

    @InjectMocks
    private RecomendacaoService recomendacaoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar recomendações baseadas no histórico de empréstimos do usuário")
    public void deveRetornarRecomendacoesBaseadasNoHistoricoDeEmprestimos() {
        Long usuarioId = 1L;
        Usuario usuario = new Usuario(usuarioId, "João Silva", "joao@email.com", LocalDate.of(2024, 1, 1), "999999999");
        
        Livro livroEmprestado1 = new Livro(1L, "O Hobbit", "J.R.R. Tolkien", "9780547928227", LocalDate.of(1937, 9, 21), "Fantasia", null);
        Livro livroEmprestado2 = new Livro(2L, "1984", "George Orwell", "9780451524935", LocalDate.of(1949, 6, 8), "Ficção Científica", null);
        
        Emprestimo emprestimo1 = new Emprestimo();
        emprestimo1.setId(1L);
        emprestimo1.setUsuario(usuario);
        emprestimo1.setLivro(livroEmprestado1);
        
        Emprestimo emprestimo2 = new Emprestimo();
        emprestimo2.setId(2L);
        emprestimo2.setUsuario(usuario);
        emprestimo2.setLivro(livroEmprestado2);
        
        List<Emprestimo> historicoEmprestimos = Arrays.asList(emprestimo1, emprestimo2);
        
        Livro livroRecomendado1 = new Livro(3L, "O Senhor dos Anéis", "J.R.R. Tolkien", "9780544003415", LocalDate.of(1954, 7, 29), "Fantasia", null);
        Livro livroRecomendado2 = new Livro(4L, "Admirável Mundo Novo", "Aldous Huxley", "9780060850524", LocalDate.of(1932, 1, 1), "Ficção Científica", null);
        Livro livroNaoRecomendado = new Livro(5L, "Dom Casmurro", "Machado de Assis", "9788525406958", LocalDate.of(1899, 1, 1), "Romance", null);
        
        List<Livro> todosLivros = Arrays.asList(livroEmprestado1, livroEmprestado2, livroRecomendado1, livroRecomendado2, livroNaoRecomendado);
        
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(emprestimoRepository.findByUsuarioId(usuarioId)).thenReturn(historicoEmprestimos);
        when(livroRepository.findAll()).thenReturn(todosLivros);
        
        List<Livro> recomendacoes = recomendacaoService.recomendaLivrosParaUsuario(usuarioId);
        
        assertEquals(2, recomendacoes.size());
        assertTrue(recomendacoes.contains(livroRecomendado1));
        assertTrue(recomendacoes.contains(livroRecomendado2));
        assertFalse(recomendacoes.contains(livroEmprestado1));
        assertFalse(recomendacoes.contains(livroEmprestado2));
        assertFalse(recomendacoes.contains(livroNaoRecomendado));
    }

    @Test
    @DisplayName("Retornar erro ao buscar recomendações para usuário nulo")
    public void retornarErroAoBuscarRecomendacoesParaUsuarioNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            recomendacaoService.recomendaLivrosParaUsuario(null);
        });
        assertEquals("O ID do usuário não pode ser nulo.", exception.getMessage());
    }

}