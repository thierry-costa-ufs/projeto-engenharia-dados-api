package com.ufs.engdados.domain.usuario.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "usuarios")
public class UsuarioDocument {

    @Id
    private String id;

    @Field("cpf")
    private Long cpf;
    @Field("nome")
    private String nome;
    @Field("dataNascimento")
    private LocalDate dataNascimento;
    @Field("email")
    private List<String> email;
    @Field("telefone")
    private List<String> telefone;
    @Field("login")
    private String login;
    @Field("senha")
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