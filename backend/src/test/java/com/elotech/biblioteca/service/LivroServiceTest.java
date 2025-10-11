package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.repository.LivroRepository;
import com.elotech.biblioteca.validator.LivroValidator;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private LivroValidator livroValidator;

    @Test
    @DisplayName("Deve retoranar uma lista com dois livros")
    public void deveRetornarUmaListaDeLivros() {
        Livro livro1 = new Livro(1L, "O Hobbit", "J.R.R. Tolkien", "9780547928227", LocalDate.of(2025, 1, 1), "Fantasia", null);
        Livro livro2 = new Livro(2L, "1984", "George Orwell", "9780451524935", LocalDate.of(2024, 5, 15), "Ficção Científica", null);
        Mockito.when(livroRepository.findAll()).thenReturn(List.of(livro1, livro2));
        List<Livro> livros = livroService.listarTodosLivros();

        Assertions.assertEquals(2, livros.size());
    }

    @Test
    @DisplayName("Deve criar um livro com sucesso")
    public void deveCriarUmLivroComSucesso() {
        Livro livroParaCriar = new Livro(null, "O Senhor dos Anéis", "J.R.R. Tolkien", "9780544003415", LocalDate.of(1954, 7, 29), "Fantasia", null);
        Livro livroSalvo = new Livro(1L, "O Senhor dos Anéis", "J.R.R. Tolkien", "9780544003415", LocalDate.of(1954, 7, 29), "Fantasia", null);
        
        Mockito.doNothing().when(livroValidator).validarLivroParaCriacao(livroParaCriar);
        Mockito.when(livroRepository.save(livroParaCriar)).thenReturn(livroSalvo);
        
        Livro resultado = livroService.criarLivro(livroParaCriar);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("O Senhor dos Anéis", resultado.getTitulo());
        assertEquals("J.R.R. Tolkien", resultado.getAutor());
        assertEquals("9780544003415", resultado.getIsbn());
        assertEquals(LocalDate.of(1954, 7, 29), resultado.getDataPublicacao());
        assertEquals("Fantasia", resultado.getCategoria());

        Mockito.verify(livroValidator).validarLivroParaCriacao(livroParaCriar);
        Mockito.verify(livroRepository).save(livroParaCriar);
    }

    @Test
    @DisplayName("Deve retorar erro ao criar livro sem categoria")
    public void deveRetornarErroAoCriarLivroSemCategoria() {
        Livro livro = new Livro(1L, "Título Exemplo", "Autor Exemplo", "1234567890123", LocalDate.of(2024, 5, 15), "", null);

        Mockito.doThrow(new IllegalArgumentException("A categoria do livro é obrigatória."))
                .when(livroValidator).validarLivroParaCriacao(livro);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            livroService.criarLivro(livro);
        });

        assertEquals("A categoria do livro é obrigatória.", exception.getMessage());
    }

}