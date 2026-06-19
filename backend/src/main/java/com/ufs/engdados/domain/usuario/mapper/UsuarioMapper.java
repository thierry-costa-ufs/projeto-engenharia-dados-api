package com.ufs.engdados.domain.usuario.mapper;

import com.ufs.engdados.domain.usuario.dto.UsuarioDTO;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import com.ufs.engdados.domain.usuario.model.relational.Usuario;

public class UsuarioMapper {

    public static Usuario toPostgresEntity(UsuarioDTO.Request dto) {
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

    public static UsuarioDocument toMongoDocument(UsuarioDTO.Request dto) {
        UsuarioDocument doc = new UsuarioDocument();
        doc.setCpf(dto.cpf());
        doc.setNome(dto.nome());
        doc.setDataNascimento(dto.dataNascimento());
        doc.setEmail(dto.email());
        doc.setTelefone(dto.telefone());
        doc.setLogin(dto.login());
        doc.setSenha(dto.senha());
        return doc;
    }

    public static UsuarioDTO.Response toResponse(Usuario pg, String mongoId, String status) {
        return new UsuarioDTO.Response(
                pg.getCpf(),
                mongoId,
                pg.getNome(),
                pg.getDataNascimento(),
                pg.getEmail(),
                pg.getTelefone(),
                pg.getLogin(),
                status
        );
    }

    public static UsuarioDTO.Response fromPostgresEntity(Usuario u) {
        return new UsuarioDTO.Response(u.getCpf(), null, u.getNome(), u.getDataNascimento(), u.getEmail(), u.getTelefone(), u.getLogin(), "APENAS_POSTGRES");
    }

    public static UsuarioDTO.Response fromMongoDocument(UsuarioDocument doc) {
        return new UsuarioDTO.Response(doc.getCpf(), doc.getId(), doc.getNome(), doc.getDataNascimento(), doc.getEmail(), doc.getTelefone(), doc.getLogin(), "APENAS_MONGO");
    }
}