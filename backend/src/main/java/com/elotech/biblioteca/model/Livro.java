package com.elotech.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "livros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @Column(nullable = false)
    @NotBlank(message = "O autor é obrigatório.")
    private String autor;

    @Column(nullable = false)
    @NotBlank(message = "O ISBN é obrigatório.")
    @Size(max = 13, message = "O ISBN só pode conter 13 caracteres.")
    private String isbn;

    @Column(name = "data_publicacao", nullable = false)
    @NotNull(message = "A data de publicação é obrigatória.")
    private LocalDate dataPublicacao;

    @Column(nullable = false)
    @NotBlank(message = "A categoria é obrigatória.")
    private String categoria;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emprestimo> emprestimos;

}