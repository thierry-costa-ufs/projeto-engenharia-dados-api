package com.ufs.engdados.domain.usuario.dto;

import java.time.LocalDate;
import java.util.List;

public interface UsuarioDTO {

    record Request(
            Long cpf,
            String nome,
            LocalDate dataNascimento,
            List<String> email,
            List<String> telefone,
            String login,
            String senha
    ) {}

    record Response(
            Long cpf,
            String mongoId,
            String nome,
            LocalDate dataNascimento,
            List<String> email,
            List<String> telefone,
            String login,
            String status
    ) {}
}