package com.ufs.engdados.domain.turma.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "turmas")
public class TurmaDocument {

    @Id
    private String id;

    @Field(name = "id_turma")
    private Integer idTurma;
    @Field(name = "cod_disc")
    private String codDisc;
    @Field(name = "numero")
    private Integer numero;
    @Field(name = "ano")
    private Short ano;
    @Field(name = "semestre")
    private Short semestre;

    public TurmaDocument() {
    }

    // getters e setters

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
    }

    public String getCodDisc() {
        return codDisc;
    }

    public void setCodDisc(String codDisc) {
        this.codDisc = codDisc;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Short getAno() {
        return ano;
    }

    public void setAno(Short ano) {
        this.ano = ano;
    }

    public Short getSemestre() {
        return semestre;
    }

    public void setSemestre(Short semestre) {
        this.semestre = semestre;
    }
}