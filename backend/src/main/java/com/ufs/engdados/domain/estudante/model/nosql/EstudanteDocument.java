package com.ufs.engdados.domain.estudante.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "estudante")
public class EstudanteDocument{

    @Id
    private String id;

    @Field("mat_estudante")
    private String matEstudante;

    @Field("cpf")
    private Long cpf;

    @Field("mc")
    private Integer mc;

    @Field("ano_ingresso")
    private Integer anoIngresso;

    // --- GETTERS E SETTERS ---


    public Integer getAnoIngresso() {
        return anoIngresso;
    }
    public void setAnoIngresso(Integer anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public Long getCpf() {
        return cpf;
    }
    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMatEstudante() {
        return matEstudante;
    }
    public void setMatEstudante(String matEstudante) {
        this.matEstudante = matEstudante;
    }

    public Integer getMc() {
        return mc;
    }
    public void setMc(Integer mc) {
        this.mc = mc;
    }
}