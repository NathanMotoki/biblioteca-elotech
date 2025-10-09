package com.elotech.biblioteca.controller;

import com.elotech.biblioteca.dto.LivroDTO;
import com.elotech.biblioteca.mapper.LivroMapper;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.service.RecomendacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recomendacoes")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    public RecomendacaoController(RecomendacaoService recomendacaoService) {
        this.recomendacaoService = recomendacaoService;
    }

    // Get Recomendações para um Usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> recomendarLivrosParaUsuario(@PathVariable Long usuarioId) {
        List<LivroDTO> dtos = LivroMapper.toDtoList(recomendacaoService.recomendaLivrosParaUsuario(usuarioId));
        if (dtos.isEmpty()) {
            return ResponseEntity.ok("Nenhuma recomendação de livro encontrada para o usuário.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
}