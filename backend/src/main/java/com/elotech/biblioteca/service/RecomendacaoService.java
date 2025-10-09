package com.elotech.biblioteca.service;

import com.elotech.biblioteca.exception.UsuarioNaoEncontradoException;
import com.elotech.biblioteca.model.Emprestimo;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.repository.EmprestimoRepository;
import com.elotech.biblioteca.repository.LivroRepository;
import com.elotech.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public RecomendacaoService(EmprestimoRepository emprestimoRepository,
                               LivroRepository livroRepository,
                               UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Listar Recomendações
    public List<Livro> recomendaLivrosParaUsuario(Long usuarioId) {

        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID " + usuarioId + " não foi encontrado."));

        List<Emprestimo> historicoEmprestimos = emprestimoRepository.findByUsuarioId(usuarioId);

        if (historicoEmprestimos.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> categoriasEmprestadas = historicoEmprestimos.stream()
                .map(emprestimo -> emprestimo.getLivro().getCategoria())
                .collect(Collectors.toSet()); // Usamos Set para remover categorias duplicadas

        Set<Long> livrosJaEmprestadosIds = historicoEmprestimos.stream()
                .map(emprestimo -> emprestimo.getLivro().getId())
                .collect(Collectors.toSet());

        List<Livro> todosLivros = livroRepository.findAll();

        List<Livro> recomendacoes = todosLivros.stream()
                .filter(livro -> categoriasEmprestadas.contains(livro.getCategoria()))
                .filter(livro -> !livrosJaEmprestadosIds.contains(livro.getId()))
                .collect(Collectors.toList());

        return recomendacoes;
    }
}