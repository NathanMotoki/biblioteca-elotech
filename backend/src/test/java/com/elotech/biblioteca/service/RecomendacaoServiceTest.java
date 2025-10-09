package com.elotech.biblioteca.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecomendacaoServiceTest {

    @InjectMocks
    private RecomendacaoService recomendacaoService;

    @Test
    @DisplayName("Retornar erro ao buscar recomendações para usuário nulo")
    public void retornarErroAoBuscarRecomendacoesParaUsuarioNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            recomendacaoService.recomendaLivrosParaUsuario(null);
        });
        assertEquals("O ID do usuário não pode ser nulo.", exception.getMessage());
    }


}