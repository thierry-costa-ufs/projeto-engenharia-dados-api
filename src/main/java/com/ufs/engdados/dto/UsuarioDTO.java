package com.ufs.engdados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long idRelacional; // Gerado pelo Postgres
    private String idNoSql;     // Gerado pelo MongoDB
    private String nome;
    private String email;
    private String senha;
}