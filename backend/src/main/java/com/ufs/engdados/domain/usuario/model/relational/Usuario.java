package com.ufs.engdados.domain.usuario.model.relational;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario", schema = "universidade")
public class Usuario {

    @Id
    private Long cpf;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @ElementCollection
    @CollectionTable(name = "usuario_email", schema = "universidade", joinColumns = @JoinColumn(name = "cpf"))
    @Column(name = "email")
    private List<String> email;

    @ElementCollection
    @CollectionTable(name = "usuario_telefone", schema = "universidade", joinColumns = @JoinColumn(name = "cpf"))
    @Column(name = "telefone")
    private List<String> telefone;

    @Column(unique = true, length = 45)
    private String login;

    @Column(length = 32)
    private String senha;

    // --- GETTERS E SETTERS ---

    public Long getCpf() { return cpf; }
    public void setCpf(Long cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public List<String> getEmail() { return email; }
    public void setEmail(List<String> email) { this.email = email; }

    public List<String> getTelefone() { return telefone; }
    public void setTelefone(List<String> telefone) { this.telefone = telefone; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}