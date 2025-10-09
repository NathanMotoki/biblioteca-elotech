// java
package com.elotech.biblioteca.controller;

import com.elotech.biblioteca.dto.EmprestimoDTO;
import com.elotech.biblioteca.mapper.EmprestimoMapper;
import com.elotech.biblioteca.model.Emprestimo;
import com.elotech.biblioteca.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<?> criarEmprestimo(@Valid @RequestBody Emprestimo emprestimo) {
        try {
            Emprestimo novoEmprestimo = emprestimoService.criarEmprestimo(emprestimo);
            EmprestimoDTO dto = EmprestimoMapper.toDto(novoEmprestimo);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            HttpStatus status = (e instanceof IllegalStateException) ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(e.getMessage(), status);
        }
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<?> registrarDevolucao(@PathVariable Long id) {
        try {
            Emprestimo emprestimoDevolvido = emprestimoService.atualizarEmprestimo(id);
            EmprestimoDTO dto = EmprestimoMapper.toDto(emprestimoDevolvido);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarTodos() {
        List<EmprestimoDTO> dtos = EmprestimoMapper.toDtoList(emprestimoService.listarTodosEmprestimos());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}