package com.elotech.biblioteca.mapper;

import com.elotech.biblioteca.dto.UsuarioDTO;
import com.elotech.biblioteca.model.Usuario;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioDTO toDto(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setDataCadastro(usuario.getDataCadastro());
        // NÃO mapear lista de emprestimos aqui
        return dto;
    }

    public static List<UsuarioDTO> toDtoList(List<Usuario> list) {
        if (list == null) return Collections.emptyList();
        return list.stream()
                .filter(Objects::nonNull)
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }
}