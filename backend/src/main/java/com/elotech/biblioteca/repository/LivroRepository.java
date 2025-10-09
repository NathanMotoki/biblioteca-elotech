package com.elotech.biblioteca.repository;

import com.elotech.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByCategoria(String categoria);

    boolean existsByIsbn(String isbn);
    Livro findByIsbn(String isbn);
}