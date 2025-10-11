package com.elotech.biblioteca.service;

import com.elotech.biblioteca.exception.UsuarioNaoEncontradoException;
import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.UsuarioRepository;
import com.elotech.biblioteca.validator.UsuarioValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioValidator usuarioValidator) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioValidator = usuarioValidator;
    }

    // Criar Usuario
    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        usuarioValidator.validarUsuarioParaCriacao(usuario);
        return usuarioRepository.save(usuario);
    }

    // Atualizar Usuario
    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioAntigo -> {
                    usuarioValidator.validarUsuarioParaAtualizacao(usuarioAtualizado, usuarioAntigo);
                    
                    if (usuarioAtualizado.getNome() != null) {
                        usuarioAntigo.setNome(usuarioAtualizado.getNome());
                    }

                    if (usuarioAtualizado.getTelefone() != null) {
                        usuarioAntigo.setTelefone(usuarioAtualizado.getTelefone());
                    }

                    if (usuarioAtualizado.getEmail() != null) {
                        usuarioAntigo.setEmail(usuarioAtualizado.getEmail());
                    }

                    return usuarioRepository.save(usuarioAntigo);
                })
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }

    // Deletar Usuario
    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com o ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }

    // Listar Usuarios
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll(); // Paginar ou colocar filtros - Nao é boa pratica
    }

}