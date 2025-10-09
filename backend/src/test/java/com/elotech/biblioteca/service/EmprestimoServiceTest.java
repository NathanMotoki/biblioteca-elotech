package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Emprestimo;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.model.StatusEmprestimo;
import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.EmprestimoRepository;
import jakarta.persistence.Id;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Test
    @DisplayName("Deve retornar uma lista de um emprestimo")
    public void deveRetornarUmaListaDeUmEmprestimo() {
        Emprestimo emprestimos = new Emprestimo(
                1L,
                new Usuario(),
                new Livro(),
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                StatusEmprestimo.ATIVO
        );
        Mockito.when(emprestimoRepository.findAll()).thenReturn(Collections.singletonList(emprestimos));
        List<Emprestimo> listaEmprestimos = emprestimoService.listarTodosEmprestimos();
        Assertions.assertEquals(1, listaEmprestimos.size());
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