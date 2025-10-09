package com.elotech.biblioteca.controller;

import com.elotech.biblioteca.dto.UsuarioDTO;
import com.elotech.biblioteca.mapper.UsuarioMapper;
import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDto) {
        Usuario usuarioEntity = UsuarioMapper.toEntity(usuarioDto);
        Usuario novo = usuarioService.criarUsuario(usuarioEntity);
        UsuarioDTO resposta = UsuarioMapper.toDto(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> dtos = UsuarioMapper.toDtoList(usuarioService.listarTodosUsuarios());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDto) {
        Usuario usuarioEntity = UsuarioMapper.toEntity(usuarioDto);
        Usuario atualizado = usuarioService.atualizarUsuario(id, usuarioEntity);
        return ResponseEntity.ok(UsuarioMapper.toDto(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}