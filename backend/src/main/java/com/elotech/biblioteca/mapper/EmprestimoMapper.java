
package com.elotech.biblioteca.mapper;

import com.elotech.biblioteca.dto.EmprestimoDTO;
import com.elotech.biblioteca.dto.LivroDTO;
import com.elotech.biblioteca.dto.UsuarioDTO;
import com.elotech.biblioteca.model.Emprestimo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class EmprestimoMapper {

    private EmprestimoMapper() {
        // Utility class
    }

    public static EmprestimoDTO toDto(Emprestimo emprestimo) {
        if (emprestimo == null) {
            return null;
        }

        UsuarioDTO usuarioDto = null;
        if (emprestimo.getUsuario() != null) {
            usuarioDto = new UsuarioDTO(); // usa o no-args gerado pelo Lombok
            usuarioDto.setId(emprestimo.getUsuario().getId());
            usuarioDto.setNome(emprestimo.getUsuario().getNome());
        }

        LivroDTO livroDto = null;
        if (emprestimo.getLivro() != null) {
            livroDto = new LivroDTO(); // usa o no-args gerado pelo Lombok
            livroDto.setId(emprestimo.getLivro().getId());
            livroDto.setTitulo(emprestimo.getLivro().getTitulo());
        }

        return new EmprestimoDTO(
                emprestimo.getId(),
                usuarioDto,
                livroDto,
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao(),
                emprestimo.getStatus()
        );
    }

    public static List<EmprestimoDTO> toDtoList(List<Emprestimo> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream()
                .filter(Objects::nonNull)
                .map(EmprestimoMapper::toDto)
                .collect(Collectors.toList());
    }
}