package com.ufs.engdados.domain.usuario.event;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.model.relational.Usuario;

public record UsuarioSalvoEvent(
        Usuario usuarioPostgres,
        UsuarioDTO.Request dtoOriginal,
        boolean ehAtualizacao
) {}