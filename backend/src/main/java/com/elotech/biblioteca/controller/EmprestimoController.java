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
    public ResponseEntity<EmprestimoDTO> criarEmprestimo(@Valid @RequestBody Emprestimo emprestimo) {
        Emprestimo novoEmprestimo = emprestimoService.criarEmprestimo(emprestimo);
        EmprestimoDTO dto = EmprestimoMapper.toDto(novoEmprestimo);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<EmprestimoDTO> registrarDevolucao(@PathVariable Long id) {
        Emprestimo emprestimoDevolvido = emprestimoService.atualizarEmprestimo(id);
        EmprestimoDTO dto = EmprestimoMapper.toDto(emprestimoDevolvido);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarTodos() {
        List<EmprestimoDTO> dtos = EmprestimoMapper.toDtoList(emprestimoService.listarTodosEmprestimos());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}