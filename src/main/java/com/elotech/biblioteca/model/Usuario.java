package com.elotech.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 200, message = "O nome deve ter no máximo 200 caracteres.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "A data de cadastro é obrigatória.")
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @NotBlank(message = "O telefone é obrigatório.")
    @Size(max = 15, message = "O telefone deve ter no máximo 15 caracteres.")
    @Column(nullable = false)
    private String telefone;
}