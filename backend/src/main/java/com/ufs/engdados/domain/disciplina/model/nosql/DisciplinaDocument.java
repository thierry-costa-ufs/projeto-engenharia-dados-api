package com.ufs.engdados.domain.disciplina.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "disciplinas")
public class DisciplinaDocument {

    @Id
    private String codDisc; // código da disciplina será o identificador único no Mongo

    private String nome;
    private Integer creditos;

    // salvamos apenas os códigos de referência no NoSQL
    private String preReq;
    private String deptoResponsavel;

    // construtor vazio exigido pelo Spring Data
    public DisciplinaDocument() {
    }

    // getters e setters

    public String getCodDisc() {
        return codDisc;
    }

    public void setCodDisc(String codDisc) {
        this.codDisc = codDisc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getPreReq() {
        return preReq;
    }

    public void setPreReq(String preReq) {
        this.preReq = preReq;
    }

    public String getDeptoResponsavel() {
        return deptoResponsavel;
    }

    public void setDeptoResponsavel(String deptoResponsavel) {
        this.deptoResponsavel = deptoResponsavel;
    }
}