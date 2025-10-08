package com.elotech.biblioteca.controller;

import com.elotech.biblioteca.model.Emprestimo;
import com.elotech.biblioteca.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    // Post Emprestimo
    @PostMapping
    public ResponseEntity<?> criarEmprestimo(@Valid @RequestBody Emprestimo emprestimo) {
        try {
            Emprestimo novoEmprestimo = emprestimoService.criarEmprestimo(emprestimo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            HttpStatus status = (e instanceof IllegalStateException) ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);
        }
    }

    // Put Emprestimo - Devolução
    @PutMapping("/{id}/devolucao")
    public ResponseEntity<?> registrarDevolucao(@PathVariable Long id) {
        try {
            Emprestimo emprestimoDevolvido = emprestimoService.atualizarEmprestimo(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get Todos Emprestimos
    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodos() {
        List<Emprestimo> emprestimos = emprestimoService.listarTodosEmprestimos();
        return new ResponseEntity<>(emprestimos, HttpStatus.OK);
    }
}
