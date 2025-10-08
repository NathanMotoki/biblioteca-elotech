package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Criar Usuario
    @Transactional
    public Usuario criarUsuario(Usuario usuario) {

        if (usuario.getDataCadastro().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de cadastro não pode ser maior que o dia atual.");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email.");
        }

        if (!isValidEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("O formato do e-mail é inválido.");
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

}