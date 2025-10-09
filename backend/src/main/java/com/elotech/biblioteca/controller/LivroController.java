package com.elotech.biblioteca.controller;

import com.elotech.biblioteca.dto.LivroDTO;
import com.elotech.biblioteca.mapper.LivroMapper;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // Post Livro
    @PostMapping
    public ResponseEntity<?> criarLivro(@Valid @RequestBody Livro livro) {
        try {
            Livro novoLivro = livroService.criarLivro(livro);
            return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get Todos os Livros
    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        List<Livro> livros = livroService.listarTodosLivros();
        List<LivroDTO> dtos = LivroMapper.toDtoList(livros);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // Atualiza Livro
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarLivro(@PathVariable Long id, @Valid @RequestBody Livro livroDetalhes) {
        try {
            Livro livroAtualizado = livroService.atualizarLivro(id, livroDetalhes);
            return new ResponseEntity<>(livroAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Deletar Livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        try {
            livroService.deletarLivro(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}