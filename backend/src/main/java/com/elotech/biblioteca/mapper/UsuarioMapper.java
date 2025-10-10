// java
package com.elotech.biblioteca.mapper;

import com.elotech.biblioteca.dto.UsuarioDTO;
import com.elotech.biblioteca.model.Usuario;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class UsuarioMapper {

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario u = new Usuario();
        u.setId(dto.getId());
        u.setNome(dto.getNome());
        u.setEmail(dto.getEmail());
        u.setTelefone(dto.getTelefone());
        u.setDataCadastro(dto.getDataCadastro());
        return u;
    }

    public static UsuarioDTO toDto(Usuario entity) {
        if (entity == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setDataCadastro(entity.getDataCadastro());
        return dto;
    }

    public static List<UsuarioDTO> toDtoList(List<Usuario> entities) {
        if (entities == null || entities.isEmpty()) return Collections.emptyList();
        return entities.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }
}
