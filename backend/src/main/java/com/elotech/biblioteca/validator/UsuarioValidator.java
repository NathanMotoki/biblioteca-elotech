package com.elotech.biblioteca.validator;

import com.elotech.biblioteca.exception.RecursoJaExisteException;
import com.elotech.biblioteca.exception.RegraNegocioException;
import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioValidator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final UsuarioRepository usuarioRepository;

    public UsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validarUsuarioParaCriacao(Usuario usuario) {
        validarCamposObrigatorios(usuario);
        validarDataCadastro(usuario.getDataCadastro());
        validarEmailUnico(usuario.getEmail());
        validarFormatoEmail(usuario.getEmail());
    }

    public void validarUsuarioParaAtualizacao(Usuario usuarioAtualizado, Usuario usuarioExistente) {
        if (usuarioAtualizado.getNome() != null && usuarioAtualizado.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }

        if (usuarioAtualizado.getTelefone() != null && usuarioAtualizado.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone do usuário é obrigatório.");
        }

        if (usuarioAtualizado.getEmail() != null) {
            validarFormatoEmail(usuarioAtualizado.getEmail());
            
            if (!usuarioAtualizado.getEmail().equals(usuarioExistente.getEmail())) {
                validarEmailUnico(usuarioAtualizado.getEmail());
            }
        }
    }

    private void validarCamposObrigatorios(Usuario usuario) {
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
    }

    private void validarDataCadastro(LocalDate dataCadastro) {
        if (dataCadastro.isAfter(LocalDate.now())) {
            throw new RegraNegocioException("A data de cadastro não pode ser maior que o dia atual.");
        }
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RecursoJaExisteException("Já existe um usuário cadastrado com este email.");
        }
    }

    private void validarFormatoEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("O formato do e-mail é inválido.");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
}