package com.ufs.engdados.domain.disciplina.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "disciplinas")
public class DisciplinaDocument {

    @Id
    private String id;

    @Field(name = "cod_disc")
    private String codDisc;
    @Field(name = "nome")
    private String nome;
    @Field(name = "pre_req")
    private String preReq;
    @Field(name = "creditos")
    private Integer creditos;
    @Field(name = "depto_responsavel")
    private String deptoResponsavel;

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