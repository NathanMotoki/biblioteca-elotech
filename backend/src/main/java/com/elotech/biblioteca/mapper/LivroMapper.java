package com.elotech.biblioteca.mapper;

import com.elotech.biblioteca.dto.LivroDTO;
import com.elotech.biblioteca.model.Livro;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class LivroMapper {

    private LivroMapper() {}

    public static LivroDTO toDto(Livro livro) {
        if (livro == null) return null;
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setIsbn(livro.getIsbn());
        dto.setDataPublicacao(livro.getDataPublicacao());
        dto.setCategoria(livro.getCategoria());
        // NÃO mapear lista de emprestimos aqui para evitar loop
        return dto;
    }

    public static List<LivroDTO> toDtoList(List<Livro> list) {
        if (list == null) return Collections.emptyList();
        return list.stream()
                .filter(Objects::nonNull)
                .map(LivroMapper::toDto)
                .collect(Collectors.toList());
    }
}