package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Test
    @DisplayName("Deve retoranar uma lista com um livro")
    public void deveRetornarUmaListaComUmLivro() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse("01/01/2025", fmt);
        Livro livro = new Livro(1L, "Título Exemplo", "Autor Exemplo", "1234567890123", data, "Categoria Exemplo", null);
        Mockito.when(livroRepository.findAll()).thenReturn(Collections.singletonList(livro));
        List<Livro> livros = livroService.listarTodosLivros();

        Assertions.assertEquals(1, livros.size());
    }

    @Test
    @DisplayName("Deve retorar erro ao criar livro sem categoria")
    public void deveRetornarErroAoCriarLivroSemCategoria() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse("01/01/2020", fmt);
        Livro livro = new Livro(1L, "Título Exemplo", "Autor Exemplo", "1234567890123", data, "", null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            livroService.criarLivro(livro);
        });

        assertEquals("A categoria do livro é obrigatória.", exception.getMessage());
    }

}