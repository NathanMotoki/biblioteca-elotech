package com.elotech.biblioteca.controller;

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

    // Post Usuarios
    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get Todos Usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Put Usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioDetalhes) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDetalhes);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete Usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}