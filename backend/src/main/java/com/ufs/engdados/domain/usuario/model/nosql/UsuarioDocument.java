package com.ufs.engdados.domain.usuario.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "usuarios")
public class UsuarioDocument {

    @Id
    private String id; // ID alfanumérico do MongoDB

    private Long cpf;
    private String nome;
    private LocalDate dataNascimento;
    private List<String> email;
    private List<String> telefone;
    private String login;
    private String senha;

    // --- GETTERS E SETTERS ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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