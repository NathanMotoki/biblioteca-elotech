package com.elotech.biblioteca.repository;

import com.elotech.biblioteca.model.Emprestimo;
import com.elotech.biblioteca.model.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Optional<Emprestimo> findByLivroIdAndStatus(Long livroId, StatusEmprestimo status);

    List<Emprestimo> findByUsuarioId(Long usuarioId);
}
