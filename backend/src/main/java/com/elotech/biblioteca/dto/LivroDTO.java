package com.elotech.biblioteca.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 300, message = "O título deve ter no máximo 300 caracteres.")
    private String titulo;

    private String autor;
    private String isbn;
    private LocalDate dataPublicacao;
    private String categoria;
}