package com.elotech.biblioteca.repository;

import com.elotech.biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Optional<Emprestimo> findByLivroIdAndStatus(Long livroId, String status);

    List<Emprestimo> findByUsuarioId(Long usuarioId);
}