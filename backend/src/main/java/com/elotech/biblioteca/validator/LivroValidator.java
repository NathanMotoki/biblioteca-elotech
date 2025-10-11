package com.elotech.biblioteca.validator;

import com.elotech.biblioteca.exception.RecursoJaExisteException;
import com.elotech.biblioteca.exception.RegraNegocioException;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LivroValidator {

    private final LivroRepository livroRepository;

    public LivroValidator(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void validarLivroParaCriacao(Livro livro) {
        validarCamposObrigatorios(livro);
        validarDataPublicacao(livro.getDataPublicacao());
        validarIsbnUnico(livro.getIsbn());
    }

    public void validarLivroParaAtualizacao(Livro livroAtualizado, Livro livroExistente) {
        if (livroAtualizado.getTitulo() != null && livroAtualizado.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do livro é obrigatório.");
        }

        if (livroAtualizado.getAutor() != null && livroAtualizado.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("O autor do livro é obrigatório.");
        }

        if (livroAtualizado.getCategoria() != null && livroAtualizado.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("A categoria do livro é obrigatória.");
        }

        if (livroAtualizado.getDataPublicacao() != null) {
            validarDataPublicacao(livroAtualizado.getDataPublicacao());
        }

        if (livroAtualizado.getIsbn() != null && !livroAtualizado.getIsbn().trim().isEmpty()) {
            // Só valida unicidade se o ISBN mudou
            if (!livroAtualizado.getIsbn().equals(livroExistente.getIsbn())) {
                Livro livroComIsbn = livroRepository.findByIsbn(livroAtualizado.getIsbn());
                if (livroComIsbn != null && !livroComIsbn.getId().equals(livroExistente.getId())) {
                    throw new RecursoJaExisteException("O novo ISBN já está em uso por outro livro.");
                }
            }
        }
    }

    private void validarCamposObrigatorios(Livro livro) {
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do livro é obrigatório.");
        }

        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("O autor do livro é obrigatório.");
        }

        if (livro.getIsbn() == null || livro.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("O ISBN do livro é obrigatório.");
        }

        if (livro.getDataPublicacao() == null) {
            throw new IllegalArgumentException("A data de publicação é obrigatória.");
        }

        if (livro.getCategoria() == null || livro.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("A categoria do livro é obrigatória.");
        }
    }

    private void validarDataPublicacao(LocalDate dataPublicacao) {
        if (dataPublicacao.isAfter(LocalDate.now())) {
            throw new RegraNegocioException("A data de publicação não pode ser futura.");
        }
    }

    private void validarIsbnUnico(String isbn) {
        if (livroRepository.existsByIsbn(isbn)) {
            throw new RecursoJaExisteException("Já existe um livro cadastrado com este ISBN.");
        }
    }
}