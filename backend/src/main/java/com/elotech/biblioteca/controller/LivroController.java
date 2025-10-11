package com.elotech.biblioteca.controller;

import com.elotech.biblioteca.dto.LivroDTO;
import com.elotech.biblioteca.dto.external.GoogleBookResponse;
import com.elotech.biblioteca.mapper.LivroMapper;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.service.LivroService;
import com.elotech.biblioteca.service.external.GoogleBooksIntegrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;
    private final GoogleBooksIntegrationService googleBooksIntegrationService;

    public LivroController(LivroService livroService, GoogleBooksIntegrationService googleBooksIntegrationService) {
        this.livroService = livroService;
        this.googleBooksIntegrationService = googleBooksIntegrationService;
    }

    // Post Livro
    @PostMapping
    public ResponseEntity<Livro> criarLivro(@Valid @RequestBody Livro livro) {
        Livro novoLivro = livroService.criarLivro(livro);
        return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
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
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @Valid @RequestBody Livro livroDetalhes) {
        Livro livroAtualizado = livroService.atualizarLivro(id, livroDetalhes);
        return new ResponseEntity<>(livroAtualizado, HttpStatus.OK);
    }

    // Deletar Livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/google-books")
    public ResponseEntity<List<GoogleBookResponse>> searchGoogleBooks(@RequestParam String query) {
        List<GoogleBookResponse> livros = googleBooksIntegrationService.buscarLivros(query);
        return ResponseEntity.ok(livros);
    }
}