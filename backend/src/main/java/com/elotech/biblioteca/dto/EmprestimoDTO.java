package com.elotech.biblioteca.dto;

import com.elotech.biblioteca.model.StatusEmprestimo;
import java.time.LocalDate;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {

    private Long id;
    private UsuarioDTO usuario;
    private LivroDTO livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;

}