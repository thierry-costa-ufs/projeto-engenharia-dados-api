package com.ufs.engdados.domain.usuario.mapper;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.model.relational.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioDTO.Request dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setLogin(dto.login());
        usuario.setSenha(dto.senha());
        return usuario;
    }

    public static UsuarioDocument toDocument(UsuarioDTO.Request dto) {
        if (dto == null) return null;
        UsuarioDocument usuario = new UsuarioDocument();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setLogin(dto.login());
        usuario.setSenha(dto.senha());
        return usuario;
    }


    public static UsuarioDTO.Response toResponse(Usuario usuario, String mongoId, String statusExecucao) {
        if (usuario == null) return null;
        return new UsuarioDTO.Response(
                mongoId,
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getLogin(),
                usuario.getSenha(),
                statusExecucao
        );
    }

    public static UsuarioDTO.Response toResponse(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO.Response(
                null,
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getLogin(),
                usuario.getSenha(),
                "ASSINCRONO"
        );
    }

    public static UsuarioDTO.Response toResponse(UsuarioDocument doc) {
        if (doc == null) return null;
        return new UsuarioDTO.Response(
                doc.getId(),
                doc.getCpf(),
                doc.getNome(),
                doc.getDataNascimento(),
                doc.getEmail(),
                doc.getTelefone(),
                doc.getLogin(),
                doc.getSenha(),
                "INTEGRADO_NOSQL"
        );
    }

}