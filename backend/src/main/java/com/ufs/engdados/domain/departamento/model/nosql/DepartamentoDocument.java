package com.ufs.engdados.domain.departamento.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "departamentos")
public class DepartamentoDocument{

    @Id
    private String id;

    @Field("cod_depto")
    private String codDepto;
    @Field("nome")
    private String nome;
    @Field("chefe")
    private String chefe;
    @Field("orcamento")
    private BigDecimal orcamento;
    @Field("comissal")
    private BigDecimal comissal;

    // --- GETTERS E SETTERS ---

    public String getChefe() {
        return chefe;
    }

    public void setChefe(String chefe) {
        this.chefe = chefe;
    }

    public String getCodDepto() {
        return codDepto;
    }

    public void setCodDepto(String codDepto) {
        this.codDepto = codDepto;
    }

    public BigDecimal getComissal() {
        return comissal;
    }

    public void setComissal(BigDecimal comissal) {
        this.comissal = comissal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(BigDecimal orcamento) {
        this.orcamento = orcamento;
    }
}