// java
package com.elotech.biblioteca.service;

import com.elotech.biblioteca.exception.UsuarioNaoEncontradoException;
import com.elotech.biblioteca.model.Emprestimo;
import com.elotech.biblioteca.model.Livro;
import com.elotech.biblioteca.model.Usuario;
import com.elotech.biblioteca.repository.EmprestimoRepository;
import com.elotech.biblioteca.repository.LivroRepository;
import com.elotech.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.elotech.biblioteca.model.StatusEmprestimo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             UsuarioRepository usuarioRepository,
                             LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public Emprestimo criarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo.getUsuario() == null || emprestimo.getLivro() == null ||
                emprestimo.getDataEmprestimo() == null || emprestimo.getStatus() == null) {
            throw new IllegalArgumentException("Todos os campos do empréstimo são obrigatórios.");
        }

        Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID " + emprestimo.getUsuario().getId() + " não foi encontrado."));

        Livro livro = livroRepository.findById(emprestimo.getLivro().getId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));

        if (emprestimo.getDataEmprestimo().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de empréstimo não pode ser maior que o dia atual.");
        }

        Optional<Emprestimo> emprestimoAtivo =
                emprestimoRepository.findByLivroIdAndStatus(livro.getId(), "ATIVO");
        if (emprestimoAtivo.isPresent()) {
            throw new IllegalStateException("Livro " + livro.getTitulo() + " já está emprestado.");
        }

        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo atualizarEmprestimo(Long id) {
        return emprestimoRepository.findById(id)
                .map(emprestimo -> {
                    if (StatusEmprestimo.DEVOLVIDO.equals(emprestimo.getStatus())) {
                        throw new IllegalStateException("O empréstimo já foi devolvido.");
                    }
                    emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
                    emprestimo.setDataDevolucao(LocalDate.now());
                    return emprestimoRepository.save(emprestimo);
                })
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoRepository.findAll();
    }
}