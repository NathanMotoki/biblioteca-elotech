package com.elotech.biblioteca.service;

import com.elotech.biblioteca.exception.UsuarioNaoEncontradoException;
import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Criar Usuario
    @Transactional
    public Usuario criarUsuario(Usuario usuario) {

        if (usuario.getDataCadastro() == null) {
            throw new IllegalArgumentException("A data de cadastro é obrigatória.");
        }

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O email do usuário é obrigatório.");
        }

        if (usuario.getTelefone() == null || usuario.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone do usuário é obrigatório.");
        }

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

    // Atualizar Usuario
    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {

        return usuarioRepository.findById(id)
                .map(usuarioAntigo -> {
                    if (usuarioAtualizado.getNome() != null) {
                        usuarioAntigo.setNome(usuarioAtualizado.getNome());
                    }

                    if (usuarioAtualizado.getTelefone() != null) {
                        usuarioAntigo.setTelefone(usuarioAtualizado.getTelefone());
                    }

                    if (usuarioAtualizado.getEmail() != null && !usuarioAtualizado.getEmail().equals(usuarioAntigo.getEmail())) {
                        if (usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
                            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email.");
                        }
                        if (!isValidEmail(usuarioAtualizado.getEmail())) {
                            throw new IllegalArgumentException("O formato do e-mail é inválido.");
                        }
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
        return usuarioRepository.findAll();
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

}