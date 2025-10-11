package com.elotech.biblioteca.service;

import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.repository.LivroRepository;
import com.elotech.biblioteca.validator.LivroValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    // Injeção de dependência
    public LivroService(LivroRepository livroRepository, LivroValidator livroValidator) {
        this.livroRepository = livroRepository;
        this.livroValidator = livroValidator;
    }

    // Criar Livro
    @Transactional
    public Livro criarLivro(Livro livro) {
        livroValidator.validarLivroParaCriacao(livro);
        return livroRepository.save(livro);
    }

    // Atualizar Livros
    @Transactional
    public Livro atualizarLivro(Long id, Livro livroAtualizado) {
        return livroRepository.findById(id)
                .map(livroAntigo -> {
                    livroValidator.validarLivroParaAtualizacao(livroAtualizado, livroAntigo);
                    
                    if (livroAtualizado.getTitulo() != null) {
                        livroAntigo.setTitulo(livroAtualizado.getTitulo());
                    }

                    if (livroAtualizado.getAutor() != null) {
                        livroAntigo.setAutor(livroAtualizado.getAutor());
                    }

                    if (livroAtualizado.getCategoria() != null) {
                        livroAntigo.setCategoria(livroAtualizado.getCategoria());
                    }

                    if (livroAtualizado.getDataPublicacao() != null) {
                        livroAntigo.setDataPublicacao(livroAtualizado.getDataPublicacao());
                    }

                    if (livroAtualizado.getIsbn() != null) {
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
    } // Paginar ou colocar filtros - Nao é boa pratica

}
