package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    // Injeção de dependência
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    // Criar Livro
    @Transactional
    public Livro criarLivro(Livro livro) {

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

        if (livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new IllegalArgumentException("Já existe um livro cadastrado com este ISBN.");
        }

        if (livro.getDataPublicacao().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de publicação não pode ser futura.");
        }

        return livroRepository.save(livro);
    }

    // Atualizar Livros
    @Transactional
    public Livro atualizarLivro(Long id, Livro livroAtualizado) {
        return livroRepository.findById(id)
                .map(livroAntigo -> {

                    if (livroAtualizado.getTitulo() != null && !livroAtualizado.getTitulo().trim().isEmpty()) {
                        livroAntigo.setTitulo(livroAtualizado.getTitulo());
                    }

                    if (livroAtualizado.getAutor() != null && !livroAtualizado.getAutor().trim().isEmpty()) {
                        livroAntigo.setAutor(livroAtualizado.getAutor());
                    }

                    if (livroAtualizado.getCategoria() != null && !livroAtualizado.getCategoria().trim().isEmpty()) {
                        livroAntigo.setCategoria(livroAtualizado.getCategoria());
                    }

                    if (livroAtualizado.getDataPublicacao() != null) {
                        if (livroAtualizado.getDataPublicacao().isAfter(LocalDate.now())) {
                            throw new IllegalArgumentException("A data de publicação não pode ser futura.");
                        }
                        livroAntigo.setDataPublicacao(livroAtualizado.getDataPublicacao());
                    }

                    if (livroAtualizado.getIsbn() != null && !livroAtualizado.getIsbn().trim().isEmpty()
                        && !livroAntigo.getIsbn().equals(livroAtualizado.getIsbn())) {
                        Livro livroComIsbn = livroRepository.findByIsbn(livroAtualizado.getIsbn());
                        if (livroComIsbn != null && !livroComIsbn.getId().equals(livroAntigo.getId())) {
                            throw new IllegalArgumentException("O novo ISBN já está em uso por outro livro.");
                        }
                        livroAntigo.setIsbn(livroAtualizado.getIsbn());
                    }
                    return livroRepository.save(livroAntigo);
                })
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + id));
    }

    // Deletar Livros
    @Transactional
    public void deletarLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado com o ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    // Listar Livros
    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

}
