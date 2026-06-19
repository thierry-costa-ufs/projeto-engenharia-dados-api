package com.ufs.engdados.domain.usuario.model.relational;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(schema = "universidade", name = "usuario")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @Column(name = "cpf", columnDefinition = "universidade.tipo_cpf")
    private Long cpf;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "email", columnDefinition = "varchar[]")
    private List<String> email;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "telefone", columnDefinition = "varchar[]")
    private List<String> telefone;

    @Column(unique = true, length = 45)
    private String login;

    @Column(length = 32)
    private String senha;
}