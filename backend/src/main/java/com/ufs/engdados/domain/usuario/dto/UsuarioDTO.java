package com.ufs.engdados.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public interface UsuarioDTO {

    record Request(
            @NotNull(message = "O CPF é obrigatório.")
            Long cpf,

            @NotBlank(message = "O nome não pode estar em branco.")
            @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
            String nome,

            @NotNull(message = "A data de nascimento é obrigatória.")
            @Past(message = "A data de nascimento deve ser uma data no passado.")
            LocalDate dataNascimento,

            @NotEmpty(message = "Pelo menos um e-mail deve ser informado.")
            List<@Email(message = "Formato de e-mail inválido.") String> email,

            @NotEmpty(message = "Pelo menos um telefone deve ser informado.")
            List<String> telefone,

            @NotBlank(message = "O login é obrigatório.")
            @Size(max = 45, message = "O login não pode exceder 45 caracteres.")
            String login,

            @NotBlank(message = "A senha é obrigatória.")
            @Size(min = 4, max = 32, message = "A senha deve conter entre 4 e 32 caracteres.")
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
            String senha,
            String status
    ) {}
}