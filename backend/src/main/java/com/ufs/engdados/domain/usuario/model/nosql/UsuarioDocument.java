package com.ufs.engdados.domain.usuario.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "usuario")
@Data
@NoArgsConstructor
public class UsuarioDocument {

    @Id
    private String id; // ID nativo do MongoDB

    @Indexed(unique = true)
    private Long cpf; // Vinculo direto com o Postgres

    private String nome;
    private LocalDate dataNascimento;
    private List<String> email;
    private List<String> telefone;
    private String login;
    private String senha;
}