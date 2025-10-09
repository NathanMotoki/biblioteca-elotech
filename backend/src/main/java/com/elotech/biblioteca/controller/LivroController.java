package com.elotech.biblioteca.controller;

import com.elotech.biblioteca.dto.LivroDTO;
import com.elotech.biblioteca.mapper.LivroMapper;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.client.RestTemplate;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;
    private final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";
    private final RestTemplate restTemplate;

    public LivroController(LivroService livroService, RestTemplate restTemplate) {
        this.livroService = livroService;
        this.restTemplate = restTemplate;
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

    @GetMapping("/google-books")
    public ResponseEntity<?> searchGoogleBooks(@RequestParam String query) {
        try {
            String url = GOOGLE_BOOKS_API_URL + "?q=" + query;
            GoogleBooksApiResponse response = restTemplate.getForObject(url, GoogleBooksApiResponse.class);
            
            if (response != null && response.getItems() != null) {
                return ResponseEntity.ok(response.getItems());
            }
            
            return ResponseEntity.ok(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar livros: " + e.getMessage());
        }
    }
}

@Data
class GoogleBooksApiResponse {
    private List<GoogleBookResponse> items;
}

@Data
class GoogleBookResponse {
    private String id;
    private VolumeInfo volumeInfo;
}

@Data
class VolumeInfo {
    private String title;
    private List<String> authors;
    private List<String> categories;
    private String publishedDate;
    private List<IndustryIdentifier> industryIdentifiers;
}

@Data
class IndustryIdentifier {
    private String type;
    private String identifier;
}